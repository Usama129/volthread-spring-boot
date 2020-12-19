package com.endpoints;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.IDListBean;
import com.communication.DeleteEmployeesResponse;
import com.communication.VolException;
import com.db.DBOperations;

@Service
@Path("/delete")
public class DeleteEndpoint {

	@DELETE  @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
	public Response deleteEmployees(@Context HttpServletRequest request, IDListBean list) throws Exception {
		
		String ip = null;
		try {
			ip =  request.getRemoteAddr();
		} catch (Exception e) {
			
		}
		System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "DELETE EMP--- request at " + new Date());
		
		DeleteEmployeesResponse response = null;
		
		try {
			response = DBOperations.deleteEmployees(list);
		} catch(VolException e) {
			response = new DeleteEmployeesResponse(e);
			System.out.println(e.getMessage());
			return Response.status((e.getMessage().contains("Expected numeric") ? 400 : 500)).entity(response).type(MediaType.APPLICATION_JSON).build();
			
		} catch (Exception e) {
			
			response = new DeleteEmployeesResponse(new VolException(e.getMessage(), -1));
			System.out.println(e.getMessage());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
		
		if (response.isSuccess()) {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Deleted " + response.getRowsChanged() + " employees");
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
		} else {
			System.out.println((ip == null ? "" : "Client: " + ip + " --- ") + "Failed to delete employees.\n" + response.getError());
			return Response.status(500).entity(response).type(MediaType.APPLICATION_JSON).build();
		}
	}
}
