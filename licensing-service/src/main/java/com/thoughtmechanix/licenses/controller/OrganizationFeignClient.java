package com.thoughtmechanix.licenses.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {

	@RequestMapping(value = "/v1/organizations", method = RequestMethod.GET)
	public String getOrganizationId();
}
