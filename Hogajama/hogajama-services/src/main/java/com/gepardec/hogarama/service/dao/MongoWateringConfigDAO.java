package com.gepardec.hogarama.service.dao;

import com.gepardec.hogarama.domain.watering.WateringConfigDAO;
import com.gepardec.hogarama.domain.watering.WateringConfigData;
import com.gepardec.hogarama.service.MongoDbProducer;
import org.mongodb.morphia.Datastore;

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
    public void update(WateringConfigData wconf) {
        save(wconf);
    }

    @Override
    public List<WateringConfigData> getAll() {
        return db.createQuery(WateringConfigData.class).asList();
    }

    @Override
    public WateringConfigData getBySensorName(String sensorName) {
        List<WateringConfigData> configs = db.createQuery(WateringConfigData.class).field("sensorName").equal(sensorName).asList();
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
