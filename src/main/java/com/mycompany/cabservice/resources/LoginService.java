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
import java.util.List;

@Path("auth")
public class LoginService {
    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(String jsonData) {
        User user = gson.fromJson(jsonData, User.class);
        if (dbUtils.emailExists(user.getEmail())) {
            return Response.status(409).entity("{\"error\": \"Email already exists\"}").build();
        }
        if (dbUtils.registerUser(user)) {
            return Response.status(201).entity("{\"message\": \"User registered successfully\"}").build();
        }
        return Response.serverError().entity("{\"error\": \"Registration failed\"}").build();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(String jsonData) {
        User credentials = gson.fromJson(jsonData, User.class);
        User user = dbUtils.validateLogin(credentials.getEmail(), credentials.getPassword());
        if (user != null) {
            return Response.ok(gson.toJson(user)).build();
        }
        return Response.status(401).entity("{\"error\": \"Invalid credentials\"}").build();
    }
}