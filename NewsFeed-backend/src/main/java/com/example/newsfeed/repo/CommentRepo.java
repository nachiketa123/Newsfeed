package com.example.newsfeed.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.newsfeed.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}
