package com.coffeeshop.coffeeshop_app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Users {
	
	@Id
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "type")
	private String type;
	@Column(name = "userID")
	private int userID;
	
	public Users() {
		userID=0;
		username="";
		password="";
		type="";
	}
	public int getUserId() {
		return userID;
	}

	public void setUserId(int userId) {
		this.userID = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
		
}