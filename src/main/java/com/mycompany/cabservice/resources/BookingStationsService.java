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
import db.BookingStations;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bookingstations")
public class BookingStationsService {
    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    // Get all booking stations
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookingStations() {
        List<BookingStations> stations = dbUtils.getBookingStations();
        return Response.status(200).entity(gson.toJson(stations)).build();
    }

    // Get distance between two specific stations
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDistance(@PathParam("from") String fromStationName, @PathParam("to") String toStationName) {
        BookingStations distance = dbUtils.getDistanceBetweenStations(fromStationName, toStationName);
        if (distance == null) {
            return Response.status(404).build();
        } else {
            return Response.status(200).entity(gson.toJson(distance)).build();
        }
    }

    // Add a new booking station distance
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBookingStation(String json) {
        BookingStations bookingStation = gson.fromJson(json, BookingStations.class);
        boolean res = dbUtils.addBookingStation(bookingStation);

        if (res) {
            return Response.status(201).build();
        } else {
            return Response.status(500).build();
        }
    }

    // Update an existing booking station distance
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBookingStation(String json) {
        BookingStations bookingStation = gson.fromJson(json, BookingStations.class);
        boolean res = dbUtils.updateBookingStation(bookingStation);

        if (res) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }

    // Delete a booking station distance
    @DELETE
    @Path("{from}/{to}")
    public Response deleteBookingStation(@PathParam("from") String fromStationName, @PathParam("to") String toStationName) {
        boolean res = dbUtils.deleteBookingStation(fromStationName, toStationName);
        if (res) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }
}