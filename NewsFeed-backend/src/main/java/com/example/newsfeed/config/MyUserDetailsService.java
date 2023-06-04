package com.example.newsfeed.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.newsfeed.customexeception.CustomException;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repo.UserRepo;

@Component
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepo userRepo;
	
	//Inside this method we write the logic to fetch the user from username if exists.	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user == null)
			throw new CustomException(HttpStatus.UNAUTHORIZED, "User does not exists");
		
		return new MyUserDetails(user) ;
	}

}
