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
import db.Car;
import db.DBUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("cars")
public class CarService {
    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars() {
        List<Car> cars = dbUtils.getCars();
        return Response.status(200).entity(gson.toJson(cars)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") int id) {
        try {
            Car car = dbUtils.getCar(id);
            if (car == null) {
                return Response.status(404).build();
            }
            return Response.status(200).entity(gson.toJson(car)).build();
        } catch (SQLException e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(String json) {
        Car car = gson.fromJson(json, Car.class);
        boolean res = dbUtils.addCar(car);

        return res ? Response.status(201).build() : Response.status(500).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCar(String json) {
        Car car = gson.fromJson(json, Car.class);
        boolean res = dbUtils.updateCar(car);

        return res ? Response.status(200).build() : Response.status(500).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCar(@PathParam("id") int id) {
        boolean res = dbUtils.deleteCar(id);
        return res ? Response.status(200).build() : Response.status(500).build();
    }
}