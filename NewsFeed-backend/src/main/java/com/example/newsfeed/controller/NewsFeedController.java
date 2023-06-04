package com.example.newsfeed.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.newsfeed.DTO.CommentUserDTO;
import com.example.newsfeed.DTO.FeedItemUser;
import com.example.newsfeed.DTO.UserDTO;
import com.example.newsfeed.service.NewsFeedService;

@RestController
public class NewsFeedController {

	@Autowired
	NewsFeedService newsFeedService;

	@RequestMapping(value = "api/newsfeed/{email}", method = RequestMethod.GET)
	public ResponseEntity<List<FeedItemUser>> getAllFeedItemsByEmail(@PathVariable String email) {
		List<FeedItemUser> list = newsFeedService.getNewsFeed(email);
		if (list == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(list);
	}

	@RequestMapping(value = "api/newsfeed/{email}", method = RequestMethod.POST)
	public ResponseEntity<FeedItemUser> addFeedItem(@PathVariable String email,
			@RequestBody Map<String, Object> rqbody) {
		String title = (String) rqbody.get("title");
		String content = (String) rqbody.get("content");
		FeedItemUser newFeed = newsFeedService.createFeed(email, title, content);
		if (newFeed == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		return ResponseEntity.ok(newFeed);

	}

	@RequestMapping(value = "api/newsfeed/feeditem/{feedItemId}/upvote", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Set<UserDTO>>> doUpvote(@PathVariable Long feedItemId,
			@RequestBody String email) {
		Map<String, Set<UserDTO>> res = newsFeedService.upvote(email, feedItemId);
		if (res == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		return ResponseEntity.ok(res);
	}

	@RequestMapping(value = "api/newsfeed/feeditem/{feedItemId}/downvote", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Set<UserDTO>>> doDownvote(@PathVariable Long feedItemId,
			@RequestBody String email) {
		Map<String, Set<UserDTO>> res = newsFeedService.downVote(email, feedItemId);
		if (res == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value = "api/newsfeed/feeditem/{feedItemId}/addComment", method = RequestMethod.POST)
	public ResponseEntity<CommentUserDTO> doDownvote(@PathVariable Long feedItemId,
			@RequestBody Map<String,Object> rq_body) {
		
		String userEmail = (String) rq_body.get("userEmail");
		String commentText = (String) rq_body.get("commentText");
		
		CommentUserDTO res = newsFeedService.addCommentToFeedItem(userEmail, feedItemId, commentText);
		if (res == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value = "api/newsfeed/feeditem/{feedItemId}/comment/{comment_id}/upvote", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Set<UserDTO>>> doUpvoteComment(@PathVariable Long feedItemId, 
			@PathVariable Long comment_id, 
			@RequestBody String userEmail){
		
		Map<String, Set<UserDTO>> res = newsFeedService.upvoteComment(userEmail, comment_id);
		if (res == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		
		return ResponseEntity.ok(res);

	}
	
	@RequestMapping(value = "api/newsfeed/feeditem/{feedItemId}/comment/{comment_id}/downvote", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Set<UserDTO>>> doDownvoteComment(@PathVariable Long feedItemId, 
			@PathVariable Long comment_id, 
			@RequestBody String userEmail){
		
		Map<String, Set<UserDTO>> res = newsFeedService.downvoteComment(userEmail, comment_id);
		if (res == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		
		return ResponseEntity.ok(res);

	}
	
	
}
