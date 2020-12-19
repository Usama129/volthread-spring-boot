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
import com.communication.VolException;
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
		 System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "ADD EMPLOYEE- request at " + new Date());
		 
		AddEmployeeResponse response = null;
		
		
		 if (employee.getId() == null || employee.getName() == null || 
				 employee.getSurname() == null || employee.getGender() == null ||
				 employee.getBirthDate() == null || employee.getJoinDate() == null ) {
			 		
			 		response = new AddEmployeeResponse(new VolException("REJECTED - Bad Params"));
					 System.out.println("REJECTED - Bad Params"); 
					 return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON).build(); 
		 }
		 
		
		try {
			response = DBOperations.addEmployee(employee);
		} catch (SQLException e) {
			response = new AddEmployeeResponse(new VolException("Server could not talk to the database"));
			System.out.println(e.getMessage());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		} catch (DateTimeParseException e) {
			response = new AddEmployeeResponse(new VolException("Server: invalid date format(s)"));
			System.out.println(e.getMessage());
			return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON).build();
		
		} catch (NumberFormatException e) {
			response = new AddEmployeeResponse(new VolException("Server: ID must be numeric"));
			System.out.println(e.getMessage());
			return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON).build();
		
		} catch (Exception e) {
			response = new AddEmployeeResponse(new VolException(e.getMessage()));
			System.out.println(e.getMessage());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
		
		if (response.isSuccess()) {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Added Employee " + response.getFullName());
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else if (response.getError().contains("already exists")){
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Rejected duplicate employee " + employee.getName() + " " + employee.getSurname());
			return Response.status(400).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Failed to add employee.\n" + response.getError());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
	}

}
