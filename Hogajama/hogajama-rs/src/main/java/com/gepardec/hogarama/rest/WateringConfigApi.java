package com.gepardec.hogarama.rest;

import com.gepardec.hogarama.domain.watering.WateringConfigData;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// TODO use generated swagger class instead of hardcoded interface
@Path("wateringconfig")
public interface WateringConfigApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllWateringConfigs();

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    Response updateWateringConfig(@Valid WateringConfigData wconf);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    Response createWateringConfig(@Valid WateringConfigData wconf);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    Response deleteWateringConfig(@Valid WateringConfigData wconf);

}
