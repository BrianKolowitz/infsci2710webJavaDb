/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.infsci2710.services;

import com.google.gson.Gson;
import edu.pitt.infsci2710.WebJdbc;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author kolobj
 */
@Path("university")
public class UniversityResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UniversityResource
     */
    public UniversityResource() {
    }

    /**
     * Retrieves representation of an instance of edu.pitt.infsci2710.servlets.UniversityResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("/departments/salaries")
    public String getDepartmentSalaries() {        
         WebJdbc db = new WebJdbc();
        try {
            List results = db.getDepartmentSalaries();
            Gson gson = new Gson();
            return gson.toJson(results);
        } catch (SQLException ex) {
            Logger.getLogger(UniversityResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @GET
    @Produces("application/json")
    @Path("/instructors")
    public String getInstructors(
        @DefaultValue("10") @QueryParam("limit") int limit,
        @DefaultValue("0") @QueryParam("offset") int offset,
        @DefaultValue("ID") @QueryParam("orderBy") String orderBy,
        @DefaultValue("DESC") @QueryParam("order") String order) {        
         WebJdbc db = new WebJdbc();
        try {
            List results = db.read(orderBy, order, limit, offset);
            Gson gson = new Gson();
            return gson.toJson(results);
        } catch (SQLException ex) {
            Logger.getLogger(UniversityResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/instructors/{id}")
    public Response deleteInstructor(
            @PathParam("id") String id) {
        
        WebJdbc db = new WebJdbc();
        Response res;
        try {
            db.delete(id);
            res = Response.accepted().build();
        } catch (SQLException ex) {
            Logger.getLogger(UniversityResource.class.getName()).log(Level.SEVERE, null, ex);
            res = Response.serverError().entity(ex).build();
        }
        return res;
    }

    /**
     * PUT method for updating or creating an instance of UniversityResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/instructors/{id}")
    public Response putInstructor(
            @PathParam("id") String id,
            String json) {
        Gson gson = new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map=(Map<String, Object>)gson.fromJson(json, map.getClass());
        WebJdbc db = new WebJdbc();
        Response res;
        String name = (String)map.get("name");
        String deptName = (String)map.get("dept_name");
        Double salary = (Double)map.get("salary");
        try {
            if( db.insert(id, name, deptName, salary) ) {
                res = Response.created(context.getAbsolutePath()).build();
            } else {
                res = Response.noContent().build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UniversityResource.class.getName()).log(Level.SEVERE, null, ex);
            res = Response.serverError().entity(ex).build();
        }
    
        return res;
    }
}
