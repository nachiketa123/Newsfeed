package com.example.newsfeed.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.newsfeed.DTO.UserDTO;
import com.example.newsfeed.customexeception.CustomException;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repo.UserRepo;

@Service
public class UserFollowService {
	
	@Autowired
	UserRepo userRepo;
	private static final Logger logger = LoggerFactory.getLogger(UserFollowService.class);
	
	public List<UserDTO> getUserSearchResult(String email, String searchText){
		
		if(searchText.length() == 0)
			throw new CustomException(HttpStatus.BAD_REQUEST, "Search text is empty");
		
		//Getting the list of users from db based on the searchText
		List<User> userList = userRepo.findByNameContainingIgnoreCaseAndEmailNot(searchText,email);
		
		if(userList == null)
			throw new CustomException(HttpStatus.NOT_FOUND, "No user found");
		
		//creating DTO object list and adding above users to be sent
		List<UserDTO> userDTOList = UserDTO.getUserDTOList(userList);
		
		//Fetching the followList of the current user in order to set isFollowedByCurrentUser property
		List<User> followList = userRepo.findByEmail(email).getFollow();
		
		//creating a hashmap for followList
		Map<Long,Boolean> followersId= new HashMap();
		
		followList.stream().forEach(user->{
			followersId.put(user.getUser_id(), true);
		});
		
		userDTOList.stream().forEach(user->{
			if(followersId.get(user.getId()) != null) {
				user.setIsFollowedByCurrentUser(true);
			}else {
				user.setIsFollowedByCurrentUser(false);
			}
		});
		
		return userDTOList;
	}
	
	public Boolean followUser(String userEmail, String toBeFollowedUserEmail) {
		
		try {
		//fetching current user to update the FollowList
		User user = userRepo.findByEmail(userEmail);
		
		//fetching user by email to be added in the followList
		User userToBeFollowed = userRepo.findByEmail(toBeFollowedUserEmail);
		
		List<User> followList = user.getFollow();
		followList.add(userToBeFollowed);
		
		user.setFollow(followList);
		
		userRepo.save(user);
		return true;
		}
		catch(Exception e) {
			logger.error("followUser() "+e.toString());
			throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong");
		}
	}
	
	public Boolean unFollowUser(String userEmail, String toBeUnFollowedUserEmail) {
		
		try {
		//fetching current user to update the FollowList
		User user = userRepo.findByEmail(userEmail);
		
		//fetching user by email to be removed from the followList
		User userToBeFollowed = userRepo.findByEmail(toBeUnFollowedUserEmail);
		
		List<User> followList = user.getFollow();
		
		Boolean res = followList.remove(userToBeFollowed);
		
		user.setFollow(followList);
		
		userRepo.save(user);
		System.out.println("result for user unfollow:"+res);
		return res;
		}
		catch(Exception e) {
			logger.error("unFollowUser() "+e.toString());
			throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong");
		}
	}
}
