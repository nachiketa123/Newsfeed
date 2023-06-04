package com.example.newsfeed.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.example.newsfeed.models.User;

public class UserDTO implements Serializable{
	private String name, email;
	private Long id;
	private Boolean isFollowedByCurrentUser;
	

	public UserDTO() {

	}

	public UserDTO(String name, String email, Long id) {
		super();
		this.name = name;
		this.email = email;
		this.id = id;
	}

	public UserDTO(User user) {
		super();
		this.name = user.getName();
		this.email = user.getEmail();
		this.id = user.getUser_id();
	}	

	public static List<UserDTO> getUserDTOList(List<User> userList) {
		List<UserDTO> userDTOList = new ArrayList<>();
		userList.stream()
				.forEach(user -> userDTOList.add(new UserDTO(user.getName(), user.getEmail(), user.getUser_id())));
		return userDTOList;
	}
	
	public static Set<UserDTO> getUserDTOList(Set<User> userList) {
		Set<UserDTO> userDTOList = new HashSet<UserDTO>();
		userList.stream()
				.forEach(user -> userDTOList.add(new UserDTO(user.getName(), user.getEmail(), user.getUser_id())));
		return userDTOList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsFollowedByCurrentUser() {
		return isFollowedByCurrentUser;
	}

	public void setIsFollowedByCurrentUser(Boolean isFollowedByCurrentUser) {
		this.isFollowedByCurrentUser = isFollowedByCurrentUser;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	

}
