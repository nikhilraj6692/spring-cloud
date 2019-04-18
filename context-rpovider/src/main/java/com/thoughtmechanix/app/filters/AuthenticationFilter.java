package com.thoughtmechanix.app.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;

		UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
		UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
		UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
		UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));

		arg2.doFilter(httpServletRequest, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
