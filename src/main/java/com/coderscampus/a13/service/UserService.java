package com.coderscampus.a13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.a13.domain.Address;
import com.coderscampus.a13.domain.User;
import com.coderscampus.a13.repository.AddressRepository;
import com.coderscampus.a13.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AddressRepository addressRepo;

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public User saveUser(User user) {
		return userRepo.save(user);

	}

	public Address saveAddress(Address address) {
		return addressRepo.save(address);

	}

	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);

	}

}
