package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailInfo {

	private static EmailInfo instance = null;
	
	private final String to, user, password, server, port;
	
	@Autowired
	private EmailInfo(@Value("${email.to}") String to, @Value("${email.user}") String user,
			@Value("${email.pass}") String password,
			@Value("${email.smtp.server}") String server, @Value("${email.smtp.port}") String port) {
		this.to = to.trim();
		this.user = user.trim();
		this.server = server.trim();
		this.port = port.trim();
		this.password = password.trim();
		EmailInfo.instance = this;
	}
	
	public static EmailInfo getInstance() throws Exception {
		if (instance == null)
			throw new Exception("No Email Info");
		return instance;
	}

	public String getTo() {
		return to;
	}

	public String getUser() {
		return user;
	}

	public String getServer() {
		return server;
	}

	public String getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}
	
}
