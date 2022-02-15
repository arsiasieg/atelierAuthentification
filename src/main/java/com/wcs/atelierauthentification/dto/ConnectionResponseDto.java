package com.wcs.atelierauthentification.dto;

import java.util.List;

import com.wcs.atelierauthentification.model.Role;

public class ConnectionResponseDto {

	private String username;
	private String token;
	private List<Role> authorities;
	
	
	
	public ConnectionResponseDto(String username, String token, List<Role> authorities) {
		super();
		this.username = username;
		this.token = token;
		this.authorities = authorities;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<Role> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}
	
}
