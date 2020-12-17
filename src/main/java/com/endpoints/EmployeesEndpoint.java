package com.endpoints;


import java.sql.SQLException;
import java.util.Date;

import com.communication.EmployeesResponse;
import com.communication.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Service;
import com.db.DBOperations;

@Service
@Path("/employees")
public class EmployeesEndpoint {
	
	
	
	@GET @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@Context HttpServletRequest request, @QueryParam("page") String page, @QueryParam("items") String items) throws Exception {
        
		
		String ip = null;
		try {
			ip =  request.getRemoteAddr();
		} catch (Exception e) {
			
		}
		
		System.out.println("GET EMPLOYEES request" + (ip == null ? "" : " from " + ip) + " at " + new Date());
		
		EmployeesResponse response = null;
		
		if (page == null || items == null || page.isEmpty() || items.isEmpty()) {
			System.out.println("REJECTED - Bad Params");
			return Response.status(400).entity("REJECTED - Bad Params").type(MediaType.APPLICATION_JSON).build();
		}
		
		try {
			int pageNo = Integer.parseInt(page.trim());
			int itemsPerPage = Integer.parseInt(items.trim());
			response = DBOperations.getEmployees(pageNo, itemsPerPage);
		} catch(SQLException e) {
			
			System.out.println(e.getMessage());
			return Response.status(500).entity(new CustomError("Server could not talk to the database")).type(MediaType.APPLICATION_JSON).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new CustomError("Server Error: " + e.getMessage())).type(MediaType.APPLICATION_JSON).build();
			
		}
		
		if (response.isSuccess()) {
			System.out.println("Returned " + response.getCount() + " items for page " + page + (ip == null ? "" : " to " + ip));
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println("Failed to fetch employees" + (ip == null ? "" : " for " + ip));
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
    }
}

