package com.example.newsfeed.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.newsfeed.models.FeedItem;
import com.example.newsfeed.models.User;

public class FeedItemUser implements Serializable {
	
	private FeedItemDTO feedItem;
	private UserDTO user;
	private Boolean isLikedByUser;
	private Boolean isDislikedByUser;
	
	public FeedItemUser() {
		
	}
	
	public FeedItemUser(FeedItem feedItem, User user, User curr_user) {
		super();
		this.feedItem = new FeedItemDTO(feedItem, curr_user);
		this.user = new UserDTO(user.getName(),user.getEmail(),user.getUser_id());
		UserDTO curr_user_dto = new UserDTO(curr_user);
		if(this.feedItem.getUpvote().contains(curr_user_dto)) {
			setIsLikedByUser(true);
			setIsDislikedByUser(false);
		}else 
		if(this.feedItem.getDownvote().contains(curr_user_dto)){
			setIsLikedByUser(false);
			setIsDislikedByUser(true);
		}
	}
	
	public static List<FeedItemUser> addUserToFeedList(List<FeedItem> feedItemlist, User user, User curr_user){
		List<FeedItemUser> feedItemUserList = new ArrayList<>();
		FeedItemUser feedItemUser;
		for(FeedItem fi: feedItemlist) {
			feedItemUser = new FeedItemUser(fi, user, curr_user);
			feedItemUserList.add(feedItemUser);
		}
		
		return feedItemUserList;
	}
	
	public FeedItemDTO getFeedItem() {
		return feedItem;
	}
	public void setFeedItem(FeedItemDTO feedItem) {
		this.feedItem = feedItem;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "FeedItemUser [feedItem=" + feedItem + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(feedItem, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeedItemUser other = (FeedItemUser) obj;
		return Objects.equals(feedItem, other.feedItem) && Objects.equals(user, other.user);
	}

	public Boolean getIsLikedByUser() {
		return isLikedByUser;
	}

	public void setIsLikedByUser(Boolean isLikedByUser) {
		this.isLikedByUser = isLikedByUser;
	}

	public Boolean getIsDislikedByUser() {
		return isDislikedByUser;
	}

	public void setIsDislikedByUser(Boolean isDislikedByUser) {
		this.isDislikedByUser = isDislikedByUser;
	}
	
	
	
	
}
