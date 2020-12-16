package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class App {

	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		//logger.info("{}", env.getProperty("JAVA_HOME"));
        //logger.info("{}", env.getProperty("app.name"));
		//System.out.println(env.getProperty("spring.datasource.username"));
	}

}
