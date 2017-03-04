package com.asgoc.common.user;

public final class User {
	
	public User(String name, AccessRights rights) {
		this.name = name;
		this.accessRights = rights;
	}
	
	public String getName() {
		return name;
	}
	
	public AccessRights getAccessRights() {
		return accessRights;
	}
	private String name;
	private AccessRights accessRights;
}
