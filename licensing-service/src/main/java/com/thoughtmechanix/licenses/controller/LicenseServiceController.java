package com.thoughtmechanix.licenses.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
@RefreshScope
public class LicenseServiceController {

	@Autowired

	private LicenseService licenseService;

	@Value("${app.property}")
	private String propertyFromYaml;

	@Autowired
	private OrganizationFeignClient orgFeignClient;

	@Autowired
	private RestTemplate loadBalancedRestTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(method = RequestMethod.GET)
	public License getLicenses(@PathVariable("organizationId") String organizationId,
			@RequestParam(required = false, value = "method") String method) {
		String id = null;
		if (method.equalsIgnoreCase("Feign")) {
			System.out.println("Feign");
			id = orgFeignClient.getOrganizationId();
		} else if (method.equalsIgnoreCase("LoadBalanced")) {
			System.out.println("Load balanced");
			ResponseEntity<String> reponse = loadBalancedRestTemplate
					.exchange("http://organizationservice/v1/organizations", HttpMethod.GET, null, String.class);
			id = reponse.getBody();
		} else if (method.equalsIgnoreCase("Discovery")) {
			System.out.println("Discovery process");
			List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
			if (instances.size() == 0)
				return null;
			String serviceUri = String.format("http://%s/v1/organizations", instances.get(0).getServiceId().toString());
			ResponseEntity<String> response = loadBalancedRestTemplate.exchange(serviceUri, HttpMethod.GET, null,
					String.class);
			id = response.getBody();
		}

		if (id == null) {
			id = "";
		}

		return new License().withId(this.propertyFromYaml).withOrganizationId(id).withProductName("Teleco")
				.withLicenseType("Seat");
	}

	@RequestMapping(value = "{licenseId}", method = RequestMethod.PUT)
	public String updateLicenses(@PathVariable("licenseId") String licenseId) {
		return String.format("This is the put");
	}

	@RequestMapping(value = "/hystrix", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "buildFallBackResponse",
			// commandProperties = {
			// @HystrixProperty(name =
			// "execution.isolation.thread.timeoutInMilliseconds", value =
			// "12000") },
			threadPoolKey = "hystrixcheckPool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "30"),
					@HystrixProperty(name = "maxQueueSize", value = "10") }, commandProperties = {
							@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
							@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
							@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
							@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
							@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") })
	public String checkHistrixMechanism() {
		System.out.println("in check hystrix");
		randomlyRunLong();
		//throw new RuntimeException();
		return String.format("This is hystrix check");
	}

	public String buildFallBackResponse() {
		System.out.println("in fallback");
		return "Fallback Response";
	}

	private void randomlyRunLong() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		if (randomNum == 3)
			sleep();
	}

	private void sleep() {
		try {
			Thread.sleep(14000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "{licenseId}", method = RequestMethod.POST)
	public String saveLicenses(@PathVariable("licenseId") String licenseId) {
		return String.format("This is the post");
	}

	@RequestMapping(value = "{licenseId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteLicenses(@PathVariable("licenseId") String licenseId) {
		return String.format("This is the Delete");
	}

}
