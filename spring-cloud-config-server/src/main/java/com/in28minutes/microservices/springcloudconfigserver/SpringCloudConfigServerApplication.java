package com.in28minutes.microservices.springcloudconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * A dedicated config server reads configuration data from an external data source like a git repo
 * The centralized configuration server can be accessed by all the microservices, and changes to the configuration are automatically propagated to all the services.
 *
 * Spring Cloud is an open-source framework for building microservices that provides a variety of tools for managing centralized configuration.
 *
 * To see a config on client side (limits-service.properties):
 * http://localhost:8888/limits-service/default
 *
 * limits-service-qa.properties:
 * http://localhost:8888/limits-service/qa
 *
 * These are diff. environments
 *
 * (JSON Formatter plugin of Chrome...)
 */

@EnableConfigServer
@SpringBootApplication
public class SpringCloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigServerApplication.class, args);
	}

}
