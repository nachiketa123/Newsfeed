package com.example.newsfeed.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.newsfeed.models.Comment;
import com.example.newsfeed.models.User;

public class CommentUserDTO implements Serializable{
	
	private UserDTO user;
	private Long comment_id,feeditem_id;
	private String comment_txt;
	private Set<UserDTO> upvote;
	private Set<UserDTO> downvote;
	private String time;
	private Boolean isLikedByTheUser;
	private Boolean isDislikedByTheUser;
	
	public CommentUserDTO() {
		
	}
	
	public CommentUserDTO(Comment comment,User curr_user) {
		super();
		this.user = new UserDTO(comment.getUser());
		
		this.comment_id = comment.getComment_id();
		this.feeditem_id = comment.getFeedItem().getFeeditem_id();
		this.comment_txt = comment.getComment_txt();
		
		this.upvote = UserDTO.getUserDTOList(comment.getUpvote());
		this.downvote = UserDTO.getUserDTOList(comment.getDownvote());
		this.setTime(comment.getTime());
		
		UserDTO curr_user_dto = new UserDTO(curr_user);
		if(this.upvote.contains(curr_user_dto)) {
			setIsLikedByTheUser(true);
			setIsDislikedByTheUser(false);
		}else 
		if(this.downvote.contains(curr_user_dto)){
			setIsLikedByTheUser(false);
			setIsDislikedByTheUser(true);
		}
	}

	public CommentUserDTO(User user, Long commentId, 
			Long feedItemId, String commentText, 
			Set<User> upvote,
			Set<User> downvote,
			String time) {
		super();
		this.user = new UserDTO(user);
		this.comment_id = commentId;
		this.feeditem_id = feedItemId;
		this.comment_txt = commentText;
		
		this.upvote = UserDTO.getUserDTOList(upvote);
		this.downvote = UserDTO.getUserDTOList(downvote);
		this.setTime(time);
	}
	
	public static List<CommentUserDTO> getCommentUserDTOList(List<Comment> comments, User curr_user){
		List<CommentUserDTO> commentUserDTOs = new ArrayList<>();
		
		comments.stream().forEach(comment->{
			commentUserDTOs.add(new CommentUserDTO(comment,curr_user));
		});
		
		return commentUserDTOs;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Long getComment_id() {
		return comment_id;
	}

	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
	}

	public Long getFeeditem_id() {
		return feeditem_id;
	}

	public void setFeeditem_id(Long feeditem_id) {
		this.feeditem_id = feeditem_id;
	}

	public String getComment_txt() {
		return comment_txt;
	}

	public void setComment_txt(String comment_txt) {
		this.comment_txt = comment_txt;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Boolean getIsLikedByTheUser() {
		return isLikedByTheUser;
	}

	public void setIsLikedByTheUser(Boolean isLikedByTheUser) {
		this.isLikedByTheUser = isLikedByTheUser;
	}

	public Boolean getIsDislikedByTheUser() {
		return isDislikedByTheUser;
	}

	public void setIsDislikedByTheUser(Boolean isDislikedByTheUser) {
		this.isDislikedByTheUser = isDislikedByTheUser;
	}
	
}
