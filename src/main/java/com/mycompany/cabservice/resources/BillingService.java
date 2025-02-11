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
import db.Billing;
import db.DBUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("billing")
public class BillingService {
    private final DBUtils dbUtils = new DBUtils();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBillings() {
        List<Billing> billings = dbUtils.getBillings();
        return Response.status(200).entity(gson.toJson(billings)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingById(@PathParam("id") int id) {
        Billing billing = dbUtils.getBillingById(id);
        if (billing == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(gson.toJson(billing)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBilling(String json) {
        Billing billing = gson.fromJson(json, Billing.class);
        boolean result = dbUtils.addBilling(billing);
        if (result) {
            return Response.status(201).build();
        } else {
            return Response.status(500).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("id") int id, String json) {
        Billing billing = gson.fromJson(json, Billing.class);
        billing.setBillingId(id);  // Ensure the billing ID is set from the URL path
        boolean result = dbUtils.updateBilling(billing);
        if (result) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteBilling(@PathParam("id") int id) {
        boolean result = dbUtils.deleteBilling(id);
        if (result) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }
}