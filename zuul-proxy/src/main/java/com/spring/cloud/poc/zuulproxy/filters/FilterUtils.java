package com.spring.cloud.poc.zuulproxy.filters;

import java.net.URI;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.zuul.context.RequestContext;

@Component
public class FilterUtils {

	public static final String CORRELATION_ID = "tmx-correlation-id";
	public static final String AUTH_TOKEN = "tmx-auth-token";
	public static final String USER_ID = "tmx-user-id";
	public static final String ORG_ID = "tmx-org-id";
	public static final String PRE_FILTER_TYPE = "pre";
	public static final String POST_FILTER_TYPE = "post";
	public static final String ROUTE_FILTER_TYPE = "route";
	public static final int PRE_DECORATION_FILTER_ORDER = 5;
	public static final String SERVICE_ID = "serviceId";
	public static final int SEND_RESPONSE_FILTER_ORDER = -100;

	public String getCorrelationId() {
		RequestContext context = RequestContext.getCurrentContext();
		UriComponentsBuilder originalRequestUriBuilder = UriComponentsBuilder
				.fromHttpRequest(new ServletServerHttpRequest(context.getRequest()));
		URI originalRequestUri = originalRequestUriBuilder.build().toUri();

		String originalPath = originalRequestUri.getPath();
		String[] pathSegments = originalPath.split("/");
		System.out.println((String) context.get(SERVICE_ID));
		
		if (context.getRequest().getHeader(CORRELATION_ID) != null) {
			return context.getRequest().getHeader(CORRELATION_ID);
		} else {
			return context.getZuulRequestHeaders().get(CORRELATION_ID);
		}
	}

	public void setCorrelationId(String correlationId) {
		RequestContext context = RequestContext.getCurrentContext();
		context.addZuulRequestHeader(CORRELATION_ID, correlationId);

	}
	
	public void setResponseCorrelationId(String correlationId) {
		RequestContext context = RequestContext.getCurrentContext();
		context.getResponse().addHeader(CORRELATION_ID, correlationId);

	}


}
