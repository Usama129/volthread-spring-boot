package com.endpoints;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.communication.CountResponse;
import com.communication.VolException;
import com.db.DBOperations;

@Service
@Path("/count")
public class CountEndpoint {
	
	
	
	@GET @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeCount(@Context HttpServletRequest request) throws Exception {
        
		
		String ip = null;
		try {
			ip =  request.getRemoteAddr();
		} catch (Exception e) {
			
		}
		System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "GET COUNT --- request at " + new Date());
		
		CountResponse response = null;
		
		try {
			response = DBOperations.getCount();
		} catch (SQLException e) {
			response = new CountResponse(new VolException("SQL Error:" + e.getMessage()));
			System.out.println(e.getMessage());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
			
		} catch (Exception e) {
			response = new CountResponse(new VolException(e.getMessage()));
			System.out.println(e.getMessage());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
			
		}
		
		if (response.isSuccess()) {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Returned count " + response.getEmployeeCount());
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Failed to get count");
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
	}
}
