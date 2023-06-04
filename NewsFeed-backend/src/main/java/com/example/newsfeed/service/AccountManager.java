package com.example.newsfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.newsfeed.DTO.UserDTO;
import com.example.newsfeed.config.JwtService;
import com.example.newsfeed.customexeception.CustomException;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repo.UserRepo;

@Service
public class AccountManager {

	private User curr_user;

	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private JwtService jwtService;

	public User getCurrentUser() {
		return this.curr_user;
	}

	public String hashPassword(String password) {

		return password;
	}

	public boolean signUp(String name, String email, String password) {

		if (userRepository.findByEmail(email) != null) {
			throw new CustomException(HttpStatus.FORBIDDEN, "This user already exists");
		} else {
			User newUser = new User(name, email, hashPassword(password));
			userRepository.save(newUser);
			curr_user = newUser;
		}
		return true;

	}

	public String login(String Enteremail, String password) {

		User userFromRepo = userRepository.findByEmail(Enteremail);

		if (userFromRepo != null) { 

			if (hashPassword(password).equals(userFromRepo.getPassword())) {
				UserDTO user = new UserDTO(userFromRepo);
//				return user;
				return jwtService.generateToken(user);
				
			}else {
				throw new CustomException(HttpStatus.UNAUTHORIZED,"Provided password is incorrect");
			}
		}
		
		//User not found
		throw new CustomException(HttpStatus.NOT_FOUND,"This email does not exist");

	}
}
