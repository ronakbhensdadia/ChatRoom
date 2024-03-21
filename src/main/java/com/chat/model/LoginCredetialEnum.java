package com.chat.model;

import lombok.Getter;

@Getter
public enum LoginCredetialEnum {

	// Hardcoded User Details
	user1("user1", "pwd1", "2bd2b846-c8cf-4c0e-92e1-04caab4b1dce"),
	user2("user2", "pwd2", "8ay8f786-f3e2-5r4h-54e1-24radd6g3hsh");

	private final String username;
	private final String pwd;
	private final String uuid;

	LoginCredetialEnum(String username, String pwd, String uuid) {
		this.username = username;
		this.pwd = pwd;
		this.uuid = uuid;
	}

}
