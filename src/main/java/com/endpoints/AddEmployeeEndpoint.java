package com.endpoints;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.EmployeeBean;
import com.communication.AddEmployeeResponse;
import com.communication.CustomError;
import com.db.DBOperations;

@Service
@CrossOrigin(origins = { "*" })
@Path("/add")
public class AddEmployeeEndpoint {

	@POST @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
	public Response addEmployee(@Context HttpServletRequest request, EmployeeBean employee) throws Exception {
		
		
		String ip = null;
		try {
			ip =  request.getRemoteAddr();
		} catch (Exception e) {
			
		}
		 System.out.println("ADD EMPLOYEE- request" + (ip == null ? "" : " from " +
				 ip) + " at " + new Date());
		 
		AddEmployeeResponse response = null;
		
		
		 if (employee.getId() == null || employee.getName() == null || 
				 employee.getSurname() == null || employee.getGender() == null ||
				 employee.getBirthDate() == null || employee.getJoinDate() == null ) {
			 
					 System.out.println("REJECTED - Bad Params"); return
					 Response.status(400).entity("REJECTED - Bad Params").type(MediaType.
					 APPLICATION_JSON).build(); 
		 }
		 
		
		try {
			response = DBOperations.addEmployee(employee);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new CustomError("Server could not talk to the database")).type(MediaType.APPLICATION_JSON).build();
		} catch (DateTimeParseException e) {
			System.out.println(e.getMessage());
			return Response.status(400).entity(new CustomError("Server: Invalid date format(s)")).type(MediaType.APPLICATION_JSON).build();
		
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return Response.status(400).entity(new CustomError("Server: ID must be numeric")).type(MediaType.APPLICATION_JSON).build();
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new CustomError("Server Error: " + e.getMessage())).type(MediaType.APPLICATION_JSON).build();
		}
		
		if (response.isSuccess()) {
			System.out.println("Added Employee " + response.getFullName() + (ip == null ? "" : " for " + ip));
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else if (response.getError().getMessage().contains("already exists")){
			System.out.println("Rejected duplicate employee " + (ip == null ? "" : " for " + ip));
			return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println("Failed to add employee " + (ip == null ? "" : " for " + ip));
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
	}

}
