package com.cmpe.project.to;

import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author NISHANT
 *
 */
public class LoginTO {
	
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
