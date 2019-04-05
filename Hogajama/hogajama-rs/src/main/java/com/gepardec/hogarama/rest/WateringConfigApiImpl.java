package com.gepardec.hogarama.rest;

import com.gepardec.hogarama.domain.watering.WateringConfigDAO;
import com.gepardec.hogarama.domain.watering.WateringConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SuppressWarnings("unused")
public class WateringConfigApiImpl implements WateringConfigApi {

    private static final Logger LOG = LoggerFactory.getLogger(WateringConfigApiImpl.class);

    @Inject
    private WateringConfigDAO configDAO;

    @Override
    public Response getAllWateringConfigs() {
        final List<WateringConfigData> configList = configDAO.getAll();
        return Response.ok(configList).build();
    }

    @Override
    public Response getWateringConfig(String sensorName) {
        final WateringConfigData config = configDAO.getBySensorName(sensorName);
        if (config == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(config).build();
        }
    }

    @Override
    public Response updateWateringConfig(String originSensorName, WateringConfigData wconf) {
        configDAO.update(originSensorName, wconf);
        return Response.ok(wconf).build();
    }

    @Override
    public Response createWateringConfig(WateringConfigData wconf) {
        configDAO.save(wconf);
        URI resourceLocation = null;
        try {
            resourceLocation = new URI("wateringconfig/" + wconf.getSensorName());
        } catch (URISyntaxException e) {
            LOG.error("Couldn't create relative resource location URI.", e);
        }
        return Response.created(resourceLocation).build();
    }

    @Override
    public Response deleteWateringConfig(String sensorName) {
        final WateringConfigData config = configDAO.getBySensorName(sensorName);
        if (config == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        configDAO.delete(config);
        return Response.noContent().build();

    }
}
