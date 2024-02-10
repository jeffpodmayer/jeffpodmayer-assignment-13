package com.coderscampus.a13.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.a13.domain.Account;
import com.coderscampus.a13.domain.Address;
import com.coderscampus.a13.domain.User;
import com.coderscampus.a13.service.AccountService;
import com.coderscampus.a13.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	// Mapping for /USERS page
	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		List<User> users = userService.findAll();
		model.put("users", users);
		return "users";
	}

	// Mapping for /REGISTER page
	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {
		model.put("user", new User());
		model.put("address", new Address());
		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user, Address address) {
		address.setUser(user);
		user.setAddress(address);
		userService.saveUser(user);
		return "redirect:/users";
	}

	// Mapping for /USERID
	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Address address = user.getAddress();
		model.put("user", user);
		model.put("address", address);
		return "update";
	}

	// UPDATE for USER
	@PostMapping("/users/{userId}")
	public String updateUser(User user, Address address) {
		user.setAddress(address);
		userService.saveUser(user);
		return "redirect:/users/" + user.getUserId();
	}

	// DELETE for USER
	@PostMapping("/users/{userId}/delete")
	public String deleteUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
	// UPON CLICKING CREATE NEW BANK ACCOUNT ----> Start off next time with a debug point
	@PostMapping("/users/{userId}/accounts")
	public String createAccount(@PathVariable Long userId) {
		User user = userService.findById(userId);
		Account account = new Account();
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountService.saveAccount(account);
		Long accountId = account.getAccountId();
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}

//	// Mapping for /ACCOUNT
//	@GetMapping("/users/{userId}/accounts/{accountId}")
//	public String createAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
//		User user = userService.findById(userId);
//		Account account = new Account();
//		accountId = accountService.findById(accountId);
//		model.put("user", user);
//		model.put("account", account);
//		return "accounts";
//	}
}
