package com.example.newsfeed.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.newsfeed.models.FeedItem;

public interface NewsFeedRepo extends JpaRepository<FeedItem, Long> {
	public List<FeedItem> findByUserId(Long id);
	
//	public List<User>
}
