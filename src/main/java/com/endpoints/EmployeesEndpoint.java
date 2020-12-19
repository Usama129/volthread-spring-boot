package com.endpoints;


import java.sql.SQLException;
import java.util.Date;

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
		
		System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "GET EMPLOYEES request at " + new Date());
		
		EmployeesResponse response = null;
		
		if (page == null || items == null || page.isEmpty() || items.isEmpty()) {
			System.out.println("REJECTED - Bad Params");
			response = new EmployeesResponse(new VolException("REJECTED - Bad Params"));
			return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
		
		try {
			int pageNo = Integer.parseInt(page.trim());
			int itemsPerPage = Integer.parseInt(items.trim());
			response = DBOperations.getEmployees(pageNo, itemsPerPage);
		} catch(SQLException e) {
			response = new EmployeesResponse(new VolException("SQL Error: Server could not talk to the database"));
			System.out.println(e.getMessage());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
			
		} catch (Exception e) {
			response = new EmployeesResponse(new VolException(e.getMessage()));
			System.out.println(e.getMessage());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
			
		}
		
		if (response.isSuccess()) {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Returned " + response.getCount() + " items for page " + page );
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Failed to fetch employees");
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
    }
}

