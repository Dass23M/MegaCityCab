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
import db.Notifications;
import db.DBUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("notifications")
public class NotificationsService {

    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotifications() {
        List<Notifications> notifications = dbUtils.getNotifications();
        return Response.status(200).entity(gson.toJson(notifications)).build();
    }
    
    // Optionally, get notifications by user id
    @GET
    @Path("user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotificationsByUser(@PathParam("userId") int userId) {
        List<Notifications> notifications = dbUtils.getNotificationsByUserId(userId);
        return Response.status(200).entity(gson.toJson(notifications)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotification(@PathParam("id") int id) {
        Notifications notification = dbUtils.getNotification(id);
        if (notification == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(gson.toJson(notification)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNotification(String json) {
        Notifications notification = gson.fromJson(json, Notifications.class);
        boolean result = dbUtils.addNotification(notification);
        if (result) {
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNotification(String json) {
        Notifications notification = gson.fromJson(json, Notifications.class);
        boolean result = dbUtils.updateNotification(notification);
        if (result) {
            return Response.status(200).build();
        }
        return Response.status(500).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteNotification(@PathParam("id") int id) {
        boolean result = dbUtils.deleteNotification(id);
        if (result) {
            return Response.status(200).build();
        }
        return Response.status(500).build();
    }
}
