package com.thoughtmechanix.app.organizationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.app.filters.UserContext;
import com.thoughtmechanix.app.filters.UserContextHolder;

@RestController
@RequestMapping(value = "v1/organizations")
@RefreshScope
public class OrganizationController {

	@RequestMapping(method = RequestMethod.GET)
	public String getOrganization() {
		System.out.println(UserContextHolder.getContext().getCorrelationId());
		return "This is get";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String updateLicenses() {
		return String.format("This is the put");
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
