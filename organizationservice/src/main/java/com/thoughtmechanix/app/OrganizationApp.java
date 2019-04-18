package com.thoughtmechanix.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = { "com.thoughtmechanix.app" })
public class OrganizationApp {
	public static void main(String[] args) {
		SpringApplication.run(OrganizationApp.class, args);
	}
}
