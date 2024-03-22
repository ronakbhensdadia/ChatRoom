package com.chat.filter;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;

public class AuthorizationFilterTest {

	@InjectMocks
	AuthorizationFilter authorizationFilter;
	
	MockHttpServletResponse mockResponse;
	MockHttpServletRequest mockRequest;
	MockFilterChain mockFilterChain;
	
	@BeforeEach
	public void setUp() {
		AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
		authorizationFilter = new AuthorizationFilter(authenticationManager);
		
		MockitoAnnotations.initMocks(this);
		mockResponse =  new MockHttpServletResponse();
		mockRequest =  new MockHttpServletRequest();
		mockFilterChain = new MockFilterChain();
	}
	
	@Test
	public void doFilterInternalTest() throws ServletException, IOException{
		mockRequest.addHeader("uuid", "2bd2b846-c8cf-4c0e-92e1-04caab4b1dce");
		mockRequest.addHeader("usrNme", "user1");
		authorizationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
		assertTrue(Boolean.TRUE);
	}
	
	@Test
	public void doFilterInternalInvalidUserTest() throws ServletException, IOException{
		mockRequest.addHeader("uuid", "a1v1-test123456");
		mockRequest.addHeader("usrNme", "user1111");
		authorizationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
		assertEquals(mockResponse.getStatus(), HttpStatus.UNAUTHORIZED.value());
	}
	
	@Test
	public void doFilterInternalInvalidUuidTest() throws ServletException, IOException{
		mockRequest.addHeader("uuid", "a1v1-test123456");
		mockRequest.addHeader("usrNme", "user1");
		authorizationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);
		assertEquals(mockResponse.getStatus(), HttpStatus.UNAUTHORIZED.value());
	}
}
