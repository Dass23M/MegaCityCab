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
import db.Categories;
import db.DBUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("categories")
public class CategoriesService {

    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        List<Categories> categories = dbUtils.getCategories();
        return Response.status(200).entity(gson.toJson(categories)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") int id) {
        Categories category = dbUtils.getCategory(id);
        if (category == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(gson.toJson(category)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(String json) {
        Categories category = gson.fromJson(json, Categories.class);
        boolean result = dbUtils.addCategory(category);
        if (result) {
            return Response.status(201).build();
        } else {
            return Response.status(500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategory(String json) {
        Categories category = gson.fromJson(json, Categories.class);
        boolean result = dbUtils.updateCategory(category);
        if (result) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteCategory(@PathParam("id") int id) {
        boolean result = dbUtils.deleteCategory(id);
        if (result) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }
}