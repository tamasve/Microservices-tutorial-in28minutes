package com.in28minutes.microservices.namingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka naming server is an application that holds information about all client service applications. Each microservice registers itself with the Eureka naming server.
 * The naming server registers the client services with their port numbers and IP addresses. It is also known as Discovery Server.
 */

@EnableEurekaServer
@SpringBootApplication
public class NamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamingServerApplication.class, args);
	}

}
