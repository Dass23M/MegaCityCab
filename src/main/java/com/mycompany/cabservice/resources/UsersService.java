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
import db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 * RESTful service for user management
 */
@Path("users")
public class UsersService {
    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    // Retrieve all users
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = dbUtils.getUsers();
        return Response.status(200).entity(gson.toJson(users)).build();
    }

    // Retrieve a single user by ID
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        try {
            User user = dbUtils.getUser(id);
            if (user == null) {
                return Response.status(404).build();
            } else {
                return Response.status(200).entity(gson.toJson(user)).build();
            }
        } catch (SQLException e) {
            return Response.status(500).build();
        }
    }

    // Add a new user
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String json) {
        User user = gson.fromJson(json, User.class);
        boolean res = dbUtils.addUser(user);

        if (res) {
            return Response.status(201).build();
        } else {
            return Response.status(500).build();
        }
    }

    // Update an existing user
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(String json) {
        User user = gson.fromJson(json, User.class);
        boolean res = dbUtils.updateUser(user);

        if (res) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }

    // Delete a user by ID
    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int id) {
        boolean res = dbUtils.deleteUser(id);
        if (res) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }
}