package com.coderscampus.a13.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		List<User> users = userService.findAll();
		model.put("users", users);
		return "users";
	}

	@GetMapping("/register")
	public String enterNewUser(ModelMap model) {
		model.put("user", new User());
		model.put("address", new Address());
		return "register";
	}

	@PostMapping("/register")
	public String createNewUser(User user, Address address) {
		userService.enterUserInfo(user, address);
		userService.saveUser(user);
		return "redirect:/users";
	}

	@GetMapping("/users/{userId}")
	public String viewUserByUserId(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Address address = user.getAddress();
		List<Account> accounts = user.getAccounts();
		model.put("user", user);
		model.put("address", address);
		model.put("accounts", accounts);
		return "update";
	}

	@PostMapping("/users/{userId}")
	public String updateUserInfo(@ModelAttribute("user") User updatedUser, Address address) {
		User existingUser = userService.findById(updatedUser.getUserId());
		userService.updateUserInfo(updatedUser, address, existingUser);
		userService.saveUser(existingUser);
		return "redirect:/users/" + existingUser.getUserId();
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}

	
	
	
	// UPON CLICKING CREATE NEW BANK ACCOUNT --> move to account controller
	@PostMapping("/users/{userId}/accounts")
	public String createAccount(@PathVariable Long userId, @ModelAttribute("account") Account account) {
		
		//Extract into accountService method
		User user = userService.findById(userId);
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountService.saveAccount(account);
		
		//Extract into accountService method
		Integer accountIndex = accountService.findAccountIndex(user.getAccounts(), account.getAccountId());
		account.setAccountName("Account # " + accountIndex);
		accountService.saveAccount(account);
		
		return "redirect:/users/" + userId + "/accounts/" + account.getAccountId();
	}

	// Mapping for /ACCOUNT --> move to account controller
	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String viewAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		User user = userService.findById(userId);
		Account account = accountService.findById(accountId);
		model.put("user", user);
		model.put("account", account);
		return "accounts";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}") // --> move to account controller
	public String updateAccount(@PathVariable Long userId, @PathVariable Long accountId, Account account) {
		accountService.saveAccount(account);
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}

}
