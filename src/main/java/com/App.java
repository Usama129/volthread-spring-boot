package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;



@SpringBootApplication
public class App {

	@Autowired
	private static Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		//logger.info("{}", env.getProperty("JAVA_HOME"));
        //logger.info("{}", env.getProperty("app.name"));
		//System.out.println(env.getProperty("spring.datasource.username"));
	}

}
