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

/**
 * REST API service for managing Cars.
 */
@Path("cars")
public class CarService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCars() {
        DBUtils utils = new DBUtils();
        List<Car> cars = utils.getCars();
        return Response.ok(new Gson().toJson(cars)).build();
    }

    @GET
    @Path("{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("carId") int carId) {
        DBUtils utils = new DBUtils();
        try {
            Car car = utils.getCar(carId);
            if (car == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(new Gson().toJson(car)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(String json) {
        Gson gson = new Gson();
        Car car = gson.fromJson(json, Car.class);
        DBUtils utils = new DBUtils();
        boolean res = utils.addCar(car);

        if (res) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCar(String json) {
        Gson gson = new Gson();
        Car car = gson.fromJson(json, Car.class);
        DBUtils utils = new DBUtils();
        boolean res = utils.updateCar(car);

        if (res) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{carId}")
    public Response deleteCar(@PathParam("carId") int carId) {
        DBUtils utils = new DBUtils();
        boolean res = utils.deleteCar(carId);

        if (res) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
