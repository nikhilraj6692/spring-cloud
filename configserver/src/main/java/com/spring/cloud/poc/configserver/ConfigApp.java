package com.spring.cloud.poc.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApp {
	public static void main(String[] args) {
		SpringApplication.run(ConfigApp.class, args);
	}
}
