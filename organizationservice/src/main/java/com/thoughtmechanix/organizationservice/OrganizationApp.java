package com.thoughtmechanix.organizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class OrganizationApp {
	public static void main(String[] args) {
		SpringApplication.run(OrganizationApp.class, args);
	}
}
