import paho.mqtt.client as paho
import time
import os
import socket, ssl
import RPi.GPIO as GPIO
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

def on_publish(client, userdata, mid):
    print("Publish returned result: {} {} {}".format(client, userdata, mid))

# Hardware SPI configuration:
SPI_PORT   = 0
SPI_DEVICE = 0
mcp = Adafruit_MCP3008.MCP3008(spi=SPI.SpiDev(SPI_PORT, SPI_DEVICE))


# Setup pins
inPin = 21 # power for moisture sensor
sensorChannel = 0 # channel to listen on ADC  
GPIO.setmode(GPIO.BCM)
GPIO.setup(inPin, GPIO.OUT)

# Setup measuring
sensorName = os.environ.get('SENSOR_NAME')
sensorType = os.environ.get('SENSOR_TYPE')
sensorLocation = os.environ.get('SENSOR_LOCATION')
waitInterval = 5
sampleInterval = 3

# Setup Hogarama connection
client = paho.Client(clean_session=True)
client.on_publish = on_publish
ssl_ctx = ssl.create_default_context(cafile='./broker.pem')
ssl_ctx.check_hostname = False
client.tls_set_context(ssl_ctx)
client.username_pw_set("mq_habarama", "mq_habarama_pass")

# Main program loop.
while True:
    try:
        client.connect("broker-amq-mqtt-ssl-57-hogarama.cloud.itandtel.at", 443, 60)
        GPIO.output(inPin, 1)
        time.sleep(sampleInterval)
        watterLevel = mcp.read_adc(sensorChannel)
        percent = 100 - int(round(watterLevel/10.24))
        print "ADC Output: {0:4d} Percentage: {1:3}%".format (watterLevel,percent)
        payload = '{{"sensorName": "{}", "type": "{}", "value": {}, "location": "{}", "version": 1 }}'
        payload = payload.format(sensorName,sensorType,percent,sensorLocation)
        client.publish("habarama", payload=payload, qos=0, retain=False)
        GPIO.output(inPin, 0)
        client.disconnect()
    except:
        print "Oops! Something wrong. Trying luck in next iteration."
    time.sleep(waitInterval)
