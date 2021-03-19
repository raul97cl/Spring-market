package com.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.market.model.User;
import com.market.repositositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public User signUp(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}
	
	public User findById(long id) {
		return repository.findById(id).orElse(null);
	}
	
	public User findByEmail(String email) {
		return repository.findFirstByEmail(email);
	}
}
