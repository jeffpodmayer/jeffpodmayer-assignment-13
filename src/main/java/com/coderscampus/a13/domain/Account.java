package com.coderscampus.a13.domain;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private Long accountId;
	private String accountName;
	
	private List<User> users = new ArrayList<>();

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
