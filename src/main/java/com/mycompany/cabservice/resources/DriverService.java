/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cabservice.resources;

/**
 *
 * @author DELL
 */
import com.google.gson.Gson;
import db.DBUtils;
import db.Driver;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RESTful service for driver management
 */
@Path("drivers")
public class DriverService {
    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrivers() {
        List<Driver> drivers = dbUtils.getDrivers();
        return Response.status(200).entity(gson.toJson(drivers)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriver(@PathParam("id") int id) {
        try {
            Driver driver = dbUtils.getDriver(id);
            if (driver == null) {
                return Response.status(404).build();
            } else {
                return Response.status(200).entity(gson.toJson(driver)).build();
            }
        } catch (SQLException e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDriver(String json) {
        Driver driver = gson.fromJson(json, Driver.class);
        boolean res = dbUtils.addDriver(driver);

        if (res) {
            return Response.status(201).build();
        } else {
            return Response.status(500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDriver(String json) {
        Driver driver = gson.fromJson(json, Driver.class);
        boolean res = dbUtils.updateDriver(driver);

        if (res) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteDriver(@PathParam("id") int id) {
        boolean res = dbUtils.deleteDriver(id);
        if (res) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }
}