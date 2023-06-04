package com.example.newsfeed.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.newsfeed.models.User;


@Repository
public interface UserRepo extends JpaRepository<User , Long> {

    public User findByEmail(String email);

	public List<User> findByNameContainingIgnoreCaseAndEmailNot( String searchText, String excludeEmail); 
	
	public List<User> findByFollowList(User user);
}
