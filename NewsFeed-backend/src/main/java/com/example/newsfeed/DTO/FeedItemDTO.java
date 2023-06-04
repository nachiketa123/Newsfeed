package com.example.newsfeed.DTO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.example.newsfeed.models.Comment;
import com.example.newsfeed.models.FeedItem;
import com.example.newsfeed.models.User;

public class FeedItemDTO implements Serializable{
	private Long feeditem_id;
	private Long user_id;
	private String title,content,time;
	private List<CommentUserDTO> comment;
	private Set<UserDTO> upvote, downvote;
	
	public FeedItemDTO() {
		
	}
	
	public FeedItemDTO(FeedItem feedItem, User curr_user) {
		super();
		this.feeditem_id = feedItem.getFeeditem_id();
		this.user_id = feedItem.getUserId() ;
		this.title = feedItem.getTitle();
		this.content = feedItem.getContent();
		this.time = feedItem.getTime();
		this.comment = CommentUserDTO.getCommentUserDTOList(feedItem.getComments(),curr_user);
		this.upvote = UserDTO.getUserDTOList(feedItem.getUpvote()); 
		this.downvote =UserDTO.getUserDTOList(feedItem.getDownvote()); 
	}
	
	public FeedItemDTO(Long feeditem_id, Long user_id, String title, String content, String time,
			List<CommentUserDTO> comment, Set<UserDTO> upvote, Set<UserDTO> downvote) {
		super();
		this.feeditem_id = feeditem_id;
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.time = time;
		this.comment = comment;
		this.upvote = upvote;
		this.downvote = downvote;
	}

	public Set<UserDTO> getUpvote() {
		return upvote;
	}

	public void setUpvote(Set<UserDTO> upvote) {
		this.upvote = upvote;
	}

	public Set<UserDTO> getDownvote() {
		return downvote;
	}

	public void setDownvote(Set<UserDTO> downvote) {
		this.downvote = downvote;
	}

	public Long getFeeditem_id() {
		return feeditem_id;
	}

	public void setFeeditem_id(Long feeditem_id) {
		this.feeditem_id = feeditem_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<CommentUserDTO> getComment() {
		return comment;
	}

	public void setComment(List<CommentUserDTO> comment) {
		this.comment = comment;
	}
	
	
	 
	
	
	
}
