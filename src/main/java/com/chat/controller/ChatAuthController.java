package com.chat.controller;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.ErrorResponse;
import com.chat.model.LoginCredetialEnum;
import com.chat.model.LoginResponse;

@RestController
@RequestMapping("/chat")
public class ChatAuthController {

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestHeader("usrNme") String userName,
			@RequestHeader("pswd") String password) {
		// Compare with Hardcoded User credentials
		if (Stream.of(LoginCredetialEnum.values()).anyMatch(u -> u.getUsername().equals(userName))) {
			LoginCredetialEnum credential = LoginCredetialEnum.valueOf(userName);
			if (!Objects.isNull(credential) && credential.getPwd().equals(password)) {
				return new ResponseEntity<>(LoginResponse.builder().uuid(credential.getUuid()).build(), HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(
				ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value()).errorMsg("Login Falied").build(),
				HttpStatus.BAD_REQUEST);
	}
}
