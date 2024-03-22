package com.chat.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ChatAuthControllerTest {

	@InjectMocks
	ChatAuthController chatAuthController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loginTest() {
		String userName = "user1";
		String password = "pwd1";
		ResponseEntity<Object> response = chatAuthController.login(userName, password);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	public void loginWrongPwdTest() {
		String userName = "user1";
		String password = "pppp";
		ResponseEntity<Object> response = chatAuthController.login(userName, password);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	}
	
	@Test
	public void loginWrongUserNameTest() {
		String userName = "uuuu";
		String password = "pwd1";
		ResponseEntity<Object> response = chatAuthController.login(userName, password);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	}
}
