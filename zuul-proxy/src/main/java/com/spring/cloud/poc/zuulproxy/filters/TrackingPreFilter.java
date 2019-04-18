package com.spring.cloud.poc.zuulproxy.filters;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;

@Component
public class TrackingPreFilter extends ZuulFilter {

	@Autowired
	private FilterUtils filterUtils;

	private static final Logger trackingLogger = LoggerFactory.getLogger(TrackingPreFilter.class);

	public Object run() {
		String correlationId = filterUtils.getCorrelationId();
		System.out.println("correlation id got from context is {}"+correlationId);
		trackingLogger.debug("correlation id got from context is {}",correlationId);
		if (null == correlationId) {
			String newId=UUID.randomUUID().toString();
			filterUtils.setCorrelationId(newId);
			System.out.println("correlation id is not present...so generating {}"+newId);
			trackingLogger.debug("correlation id is not present...so generating {}",newId);
		}
		return null;
	}

	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return FilterUtils.PRE_DECORATION_FILTER_ORDER + 1;
	}

	@Override
	public String filterType() {
		return FilterUtils.PRE_FILTER_TYPE;
	}

}
