package com.example.newsfeed.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMMENTS")
public class Comment {


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long comment_id;
    private String comment_txt;
    private String time;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "feeditem_id")
    private FeedItem  feedItem;
    
    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "upvote_comment",
    joinColumns = @JoinColumn(name = "comment_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> upvote;
    
    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "downvote_comment",
    joinColumns = @JoinColumn(name = "comment_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> downvote;



    Comment(){

    }
    
    public Comment(String comment_txt, User user, FeedItem feedItem) {
		super();
		this.comment_txt = comment_txt;
		this.user = user;
		this.feedItem = feedItem;
		this.upvote = new HashSet<>();
		this.downvote = new HashSet<>();
		this.time = LocalDateTime.now().toString();
	}
    
    
    public Comment(Long comment_id, String comment_txt, User user, FeedItem feedItem, Set<User> upvote,
			Set<User> downvote) {
		super();
		this.comment_id = comment_id;
		this.comment_txt = comment_txt;
		this.user = user;
		this.feedItem = feedItem;
		this.upvote = upvote;
		this.downvote = downvote;
		this.time = LocalDateTime.now().toString();
	}


	public Comment(FeedItem feedItem, String comment_txt ){
         this.comment_txt = comment_txt;
         this.feedItem = feedItem;
    }
    
    public Long getComment_id() {
		return comment_id;
	}

	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment_txt() {
		return comment_txt;
	}

	public void setComment_txt(String comment_txt) {
		this.comment_txt = comment_txt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FeedItem getFeedItem() {
		return feedItem;
	}

	public void setFeedItem(FeedItem feedItem) {
		this.feedItem = feedItem;
	}
	
	

	public Set<User> getUpvote() {
		return upvote;
	}


	public void setUpvote(Set<User> upvote) {
		this.upvote = upvote;
	}


	public Set<User> getDownvote() {
		return downvote;
	}


	public void setDownvote(Set<User> downvote) {
		this.downvote = downvote;
	}


	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", comment_txt=" + comment_txt + ", user_id=" + user.getUser_id()
				+ ", feedItem=" + feedItem.getFeeditem_id() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment_id, comment_txt, feedItem.getFeeditem_id(), user.getUser_id());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(comment_id, other.comment_id);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
    
    

}
