package com.example.newsfeed.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.newsfeed.DTO.UserDTO;
import com.example.newsfeed.customexeception.CustomException;
import com.example.newsfeed.models.User;
import com.example.newsfeed.service.AccountManager;

@RestController
public class UserAuth {
	
	@Autowired
	AccountManager accountManager;
	
	@RequestMapping(value = "api/user/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String,String>> doLogin(@RequestBody User user) {
		
		String res = accountManager.login(user.getEmail(), user.getPassword());
		Map json = new HashMap<>();
		json.put("token", res);
		return ResponseEntity.ok(json);
		
		
	}
	
	@RequestMapping(value = "api/user/register", method = RequestMethod.POST)
	public Boolean doRegisterUser(@RequestBody User user) {
		Boolean res = accountManager.signUp(user.getName(),user.getEmail(), user.getPassword());
		return res;
	}
}
