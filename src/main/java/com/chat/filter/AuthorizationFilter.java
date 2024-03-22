package com.chat.filter;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.chat.model.ErrorResponse;
import com.chat.model.LoginCredetialEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String uuid = request.getHeader("uuid");
		String userName = request.getHeader("usrNme");

		if (Stream.of(LoginCredetialEnum.values()).anyMatch(u -> u.getUsername().equals(userName))) {
			LoginCredetialEnum user = LoginCredetialEnum.valueOf(userName);
			if (user.getUuid().equalsIgnoreCase(uuid)) {
				filterChain.doFilter(request, response);
			} else {
				ErrorResponse error = ErrorResponse.builder().errorCode(HttpStatus.UNAUTHORIZED.value())
						.errorMsg("User Not Authorized").build();
				response.getWriter().write(new ObjectMapper().writeValueAsString(error));
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		} else {
			ErrorResponse error = ErrorResponse.builder().errorCode(HttpStatus.UNAUTHORIZED.value())
					.errorMsg("User Not Authorized").build();
			response.getWriter().write(new ObjectMapper().writeValueAsString(error));
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

}
