package com.example.centralizedconfigurationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CentralizedConfigurationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralizedConfigurationServiceApplication.class, args);
	}

}
