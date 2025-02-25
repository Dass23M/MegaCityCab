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
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cars")
public class CarService {
    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    // Get all cars
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars() {
        List<Car> cars = dbUtils.getCars();
        return Response.status(200).entity(gson.toJson(cars)).build();
    }

    // Get a car by ID
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") int id) {
        try {
            Car car = dbUtils.getCar(id);
            if (car == null) {
                return Response.status(404).entity("{\"message\":\"Car not found\"}").build();
            }
            return Response.status(200).entity(gson.toJson(car)).build();
        } catch (SQLException e) {
            return Response.status(500).entity("{\"message\":\"Internal server error\"}").build();
        }
    }

    // Add a new car
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCar(String json) {
        Car car = gson.fromJson(json, Car.class);
        boolean result = dbUtils.addCar(car);
        if (result) {
            return Response.status(201).entity("{\"message\":\"Car created successfully\"}").build();
        } else {
            return Response.status(500).entity("{\"message\":\"Failed to create car\"}").build();
        }
    }

    // Update an existing car
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCar(String json) {
        Car car = gson.fromJson(json, Car.class);
        boolean result = dbUtils.updateCar(car);
        if (result) {
            return Response.status(200).entity("{\"message\":\"Car updated successfully\"}").build();
        } else {
            return Response.status(500).entity("{\"message\":\"Failed to update car\"}").build();
        }
    }

    // Delete a car by ID
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("id") int id) {
        boolean result = dbUtils.deleteCar(id);
        if (result) {
            return Response.status(200).entity("{\"message\":\"Car deleted successfully\"}").build();
        } else {
            return Response.status(500).entity("{\"message\":\"Failed to delete car\"}").build();
        }
    }
}