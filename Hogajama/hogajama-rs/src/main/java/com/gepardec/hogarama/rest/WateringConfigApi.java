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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{sensorName}")
    Response getWateringConfig(@PathParam("sensorName") String sensorName);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{originSensorName}")
    Response updateWateringConfig(@PathParam("originSensorName") String originSensorName, @Valid WateringConfigData wconf);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    Response createWateringConfig(@Valid WateringConfigData wconf);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{sensorName}")
    Response deleteWateringConfig(@PathParam("sensorName") String sensorName);

}
