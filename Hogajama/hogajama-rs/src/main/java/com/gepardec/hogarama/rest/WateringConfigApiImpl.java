package com.gepardec.hogarama.rest;

import com.gepardec.hogarama.domain.watering.WateringConfigDAO;
import com.gepardec.hogarama.domain.watering.WateringConfigData;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class WateringConfigApiImpl implements WateringConfigApi {

    @Inject
    private WateringConfigDAO configDAO;

    @Override
    public Response getAllWateringConfigs() {
        final List<WateringConfigData> configList = configDAO.getAll();
        return Response.ok(configList).build();
    }

    @Override
    public Response updateWateringConfig(WateringConfigData wconf) {
        configDAO.update(wconf);
        return Response.ok(wconf).build();
    }

    @Override
    public Response createWateringConfig(WateringConfigData wconf) {
        configDAO.save(wconf);
        return Response.
                status(Response.Status.CREATED)
                .entity(wconf)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @Override
    public Response deleteWateringConfig(WateringConfigData wconf) {
        configDAO.delete(wconf);
        return Response.noContent().build();
    }
}
