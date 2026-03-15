package com.authentication.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

/**
 * Entry point for the Auth microservice of the TaskFlow application.
 *
 * <p>This service is responsible for user authentication and authorization.
 * It registers with the Eureka service registry and exposes REST endpoints
 * consumed by other TaskFlow microservices via OpenFeign clients.
 *
 * <p>Enabled features:
 * <ul>
 *   <li>Spring Security – JWT-based authentication</li>
 *   <li>Spring Data JPA – user and role persistence (MySQL)</li>
 *   <li>Eureka Client – service discovery registration</li>
 *   <li>OpenFeign – inter-service HTTP communication</li>
 * </ul>
 */

@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AuthApplication {

	/**
	 * Bootstraps the Spring Boot application context and starts the embedded server.
	 *
	 * @param args optional command-line arguments passed at startup
	 */
	public static void main(String[] args) {

		log.info("***[AUTH-SERVICE] :: [AuthApplication] :: [main] :: Starting Auth Service ***");
		SpringApplication.run(AuthApplication.class, args);
	}

}
