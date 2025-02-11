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
import db.Booking;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bookings")
public class BookingService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings() {
        DBUtils utils = new DBUtils();
        List<Booking> bookings = utils.getBookings();
        
        Gson gson = new Gson();
        return Response
                .status(200)
                .entity(gson.toJson(bookings))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("id") int id) {
        DBUtils utils = new DBUtils();
        
        try {
            Booking booking = utils.getBooking(id);
            if (booking == null) {
                return Response.status(404).build();
            } else {
                Gson gson = new Gson();
                return Response.status(200).entity(gson.toJson(booking)).build();
            }
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBooking(String json) {
        Gson gson = new Gson();
        Booking booking = gson.fromJson(json, Booking.class);
        DBUtils utils = new DBUtils();
        boolean result = utils.addBooking(booking);
        
        if (result) {
            return Response.status(201).build();
        } else {
            return Response.status(500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBooking(String json) {
        Gson gson = new Gson();
        Booking booking = gson.fromJson(json, Booking.class);
        DBUtils utils = new DBUtils();
        boolean result = utils.updateBooking(booking);
        
        if (result) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteBooking(@PathParam("id") int id) {
        DBUtils utils = new DBUtils();
        boolean result = utils.deleteBooking(id);
        
        if (result) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }
}