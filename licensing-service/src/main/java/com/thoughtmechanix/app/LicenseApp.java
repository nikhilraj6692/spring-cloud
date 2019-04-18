package com.thoughtmechanix.app;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.thoughtmechanix.app.filters.UserContextInterceptor;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@ComponentScan(basePackages = { "com.thoughtmechanix.app" })
public class LicenseApp {
	public static void main(String[] args) {
		SpringApplication.run(LicenseApp.class, args);
	}

	@LoadBalanced
	@Bean(name = "loadBalancedRestTemplate")
	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate();
		List interceptors = template.getInterceptors();
		if (interceptors == null) {
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		} else {
			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}

		return template;
	}
}
