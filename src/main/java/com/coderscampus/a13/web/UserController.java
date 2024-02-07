package com.coderscampus.a13.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.a13.domain.Address;
import com.coderscampus.a13.domain.User;
import com.coderscampus.a13.service.UserService;

@Controller
public class UserController {

	@Autowired 
	private UserService userService;
	
	//Mapping for /USERS page
	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		List<User> users = userService.findAll();
		model.put("users", users);
		return "users";
	}
	
	//Mapping for /REGISTER page
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
	
	//Mapping for /USERID 
	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Address address = user.getAddress();
		model.put("user", user);
		model.put("address", address);
		return "update";
	}
	
	@PostMapping("/users/{userId}")//NEED TO ADD A SAVE ADDRESS METHOD
	public String updateUser(User user) {
		userService.saveUser(user);
		return "redirect:/users{userId}";
	}
	
	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
	
	//TO-DO
	//Add save address method to updateUser
}
