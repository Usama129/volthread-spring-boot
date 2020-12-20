package com.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBInfo {

	private static DBInfo instance = null;
	
	private final String driver, host, port, schema, user, pass;

    @Autowired
    private DBInfo(@Value("${sql.driver}") String driver, @Value("${sql.host}") String host,
    		@Value("${sql.port}") String port, @Value("${sql.schema}") String schema,
    		@Value("${sql.user}") String user, @Value("${sql.pass}") String pass) {
    	
		        this.driver = driver.trim();
		        this.host = host.trim();
		        this.port = port.trim();
		        this.schema = schema.trim();
		        this.user = user.trim();
		        this.pass = pass.trim();
		        DBInfo.instance = this;
    }

	public static DBInfo getInstance() throws Exception {
		if (instance == null)
			throw new Exception("No DB Info");
		return instance;
	}

	public String getDriver() {
		return driver;
	}
	
	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public String getSchema() {
		return schema;
	}
}
