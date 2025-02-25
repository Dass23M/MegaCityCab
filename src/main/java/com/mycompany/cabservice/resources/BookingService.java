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
import db.Booking;
import db.DBUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bookings")
public class BookingService {

    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings() {
        List<Booking> bookings = dbUtils.getBookings();
        return Response.status(200).entity(gson.toJson(bookings)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("id") int id) {
        Booking booking = dbUtils.getBooking(id);
        if (booking == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(gson.toJson(booking)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBooking(String json) {
        Booking booking = gson.fromJson(json, Booking.class);
        boolean result = dbUtils.addBooking(booking);
        if (result) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBooking(String json) {
        Booking booking = gson.fromJson(json, Booking.class);
        boolean result = dbUtils.updateBooking(booking);
        if (result) {
            return Response.status(200).build();
        }
        return Response.status(500).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBooking(@PathParam("id") int id) {
        boolean result = dbUtils.deleteBooking(id);
        if (result) {
            return Response.status(200).build();
        }
        return Response.status(500).build();
    }
}