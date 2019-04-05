package com.gepardec.hogarama.service.dao;

import com.gepardec.hogarama.domain.watering.WateringConfigDAO;
import com.gepardec.hogarama.domain.watering.WateringConfigData;
import com.gepardec.hogarama.service.MongoDbProducer;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;

import javax.inject.Inject;
import java.util.List;

public class MongoWateringConfigDAO implements WateringConfigDAO {

    @Inject
    public Datastore db;

    public MongoWateringConfigDAO() {
    }

    @Override
    public void save(WateringConfigData wconf) {
        db.save(wconf);
    }

    @Override
    public void delete(WateringConfigData wconf) {
        db.delete(wconf);
    }

    @Override
    public void update(String originSensorName, WateringConfigData wconf) {
        // as we use the sensorName as identifier we need to check if the sensorName is updated or not
        // if the sensorName is assigend to a new value, we need to delete the origin entry first,
        // because _id is an immutable field
        if (!originSensorName.equals(wconf.getSensorName())) {
            delete(getBySensorName(originSensorName));
        }
        save(wconf);
    }

    @Override
    public List<WateringConfigData> getAll() {
        return db.createQuery(WateringConfigData.class).asList();
    }

    @Override
    public WateringConfigData getBySensorName(String sensorName) {
        List<WateringConfigData> configs = db.createQuery(WateringConfigData.class).field(Mapper.ID_KEY).equal(sensorName).asList();
        if (configs.isEmpty()) {
            return null;
        }
        return configs.get(0);
    }

    public void setUpForTest() {
        MongoDbProducer producer = new MongoDbProducer();
        db = producer.getDatastore();
    }

}
