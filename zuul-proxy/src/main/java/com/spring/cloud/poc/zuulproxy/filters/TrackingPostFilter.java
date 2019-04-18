package com.spring.cloud.poc.zuulproxy.filters;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;

@Component
public class TrackingPostFilter extends ZuulFilter {

	@Autowired
	private FilterUtils filterUtils;

	private static final Logger trackingLogger = LoggerFactory.getLogger(TrackingPostFilter.class);

	public Object run() {
		String correlationId = filterUtils.getCorrelationId();
		System.out.println("correlation id got from context is {}" + correlationId);
		trackingLogger.debug("correlation id got from context is {}", correlationId);
		filterUtils.setResponseCorrelationId(correlationId);
		return null;
	}

	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return FilterUtils.SEND_RESPONSE_FILTER_ORDER + 1;
	}

	@Override
	public String filterType() {
		return FilterUtils.POST_FILTER_TYPE;
	}

}
