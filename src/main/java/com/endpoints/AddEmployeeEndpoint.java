package com.endpoints;

import java.sql.SQLException;
import java.util.Date;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.communication.AddEmployeeResponse;
import com.communication.CustomError;
import com.db.DBOperations;

@Service
@Path("/add")
public class AddEmployeeEndpoint {
	
	@POST @Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(@Context HttpHeaders httpHeaders,
			@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("gender") String gender,
			@QueryParam("birthDate") String birthDate, @QueryParam("joinDate") String joinDate) throws Exception {
		
		String ip =  httpHeaders.getHeaderString("x-forwarded-for");
		System.out.println("ADD EMPLOYEE- request" + (ip == null ? "" : " from " + ip) + " at " + new Date());
	
		AddEmployeeResponse response = null;
		
		try {
			response = DBOperations.addEmployee(id, name, surname, gender, birthDate, joinDate);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new CustomError("Server could not talk to the database")).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new CustomError("Server Error: " + e.getMessage())).type(MediaType.APPLICATION_JSON).build();
		}
		
		if (response.isSuccess()) {
			System.out.println("Added Employee " + response.getFullName() + (ip == null ? "" : " for " + ip));
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println("Failed to add employee " + (ip == null ? "" : " for " + ip));
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
	}
	
}
