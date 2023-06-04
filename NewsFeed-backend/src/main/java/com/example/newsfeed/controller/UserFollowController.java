package com.example.newsfeed.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.newsfeed.DTO.UserDTO;
import com.example.newsfeed.service.UserFollowService;

@RestController
public class UserFollowController {
	
	@Autowired
	UserFollowService userFollowService;
	
	@RequestMapping(value = "api/search-user/{email}")
	public ResponseEntity<List<UserDTO>> doSearchUser(@PathVariable String email, @RequestParam String searchText){
		List<UserDTO> searchResult = userFollowService.getUserSearchResult(email,searchText);
		
		if(searchResult == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(searchResult);
	}
	
	@RequestMapping(value="api/user/follow", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> doFollowUser(@RequestBody Map<String,Object> rq_body){
		String userEmail = (String) rq_body.get("userEmail");
		String userToBeFollowed = (String) rq_body.get("userToBeFollowed");
		
		Boolean res = userFollowService.followUser(userEmail, userToBeFollowed);
		if(res == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value="api/user/unfollow", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> doUnFollowUser(@RequestBody Map<String,Object> rq_body){
		String userEmail = (String) rq_body.get("userEmail");
		String userToBeUnFollowed = (String) rq_body.get("userToBeUnFollowed");
		
		Boolean res = userFollowService.unFollowUser(userEmail, userToBeUnFollowed);
		if(res == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(res);
	}
}
