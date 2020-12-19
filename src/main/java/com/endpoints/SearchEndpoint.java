package com.endpoints;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.communication.EmployeesResponse;
import com.communication.VolException;
import com.db.DBOperations;


@Service
@Path("/search")
public class SearchEndpoint {
	
	@GET @Produces(MediaType.APPLICATION_JSON)
	public Response searchEmployees(@Context HttpServletRequest request, @QueryParam("search") String search, @QueryParam("items") String items) {
		
		String ip = null;
		try {
			ip =  request.getRemoteAddr();
		} catch (Exception e) {
			
		}
		System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "SEARCH EMP--- request at " + new Date());
		
		
		EmployeesResponse response = null;
		
		
		if (search == null || Integer.parseInt(items) < 0 ) {
			System.out.println("REJECTED - Bad Params");
			response = new EmployeesResponse(new VolException("REJECTED - Bad Params"));
			return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
			
		try {
			response = DBOperations.getEmployees(search, Integer.parseInt(items));
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
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Returned " + response.getCount() + " items for search " + search);
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Failed to fetch employees");
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
		
	}
	
	
}
