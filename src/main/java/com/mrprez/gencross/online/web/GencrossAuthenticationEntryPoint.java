package com.mrprez.gencross.online.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class GencrossAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public GencrossAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		if (request.getRequestURI().contains("/include/")) {
			response.sendError(403);
		} else {
			super.commence(request, response, authException);
		}
	}

}
