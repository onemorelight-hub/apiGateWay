package com.example.apiGateWay.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.apiGateWay.exception.APIServiceException;
import com.example.apiGateWay.jwt.JwtTokenValidator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PreFilter extends ZuulFilter {

	@Autowired
	JwtTokenValidator jwtTokenValidator;
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String token = request.getHeader("Authorization");
		System.out.println("Header: "+token);
		System.out.println("PreFilter.run(): request: "+request.getMethod() + "requestUrl: "+request.getRequestURI().toString());
		if(!jwtTokenValidator.validate(token)) {
			System.out.println("Invalid token from client");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody("{\"error\":\"Sorry No Entry . Rejected\"}");
			ctx.setRouteHost(null);
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
}
