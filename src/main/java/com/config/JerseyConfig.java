package com.config;

import com.endpoints.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(ResponseFilter.class); // to allow CORS
		register(AddEmployeeEndpoint.class);
		register(EmployeesEndpoint.class);
		register(CountEndpoint.class);
		register(DeleteEndpoint.class);
	}
}