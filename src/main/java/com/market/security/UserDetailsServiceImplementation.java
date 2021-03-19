package com.market.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;


import com.market.repositositories.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	UserRepository repository;
	
	
	//Build a UserDetails for users authentification
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.market.model.User user = repository.findFirstByEmail(username);
		UserBuilder builder = null;
		
		if(user != null) {
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(user.getPassword());	
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		}else {
			throw new UsernameNotFoundException("User not found");
		}
		
		return builder.build();
	}

}
