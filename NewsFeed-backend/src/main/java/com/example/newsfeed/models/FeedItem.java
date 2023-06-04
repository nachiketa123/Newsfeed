package com.example.newsfeed.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="FEEDITEM")
public class FeedItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long feeditem_id;
	
    private Long userId;
    private String title; 
    
    @Lob
    @Column(length = 1000000000)
    private String content;
    private String time;
    
    @OneToMany(mappedBy = "feedItem")
    @Fetch(FetchMode.JOIN)
    private List<Comment> comment;
    
    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "upvote_users",
    joinColumns = @JoinColumn(name = "feeditem_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> upvote;
    
    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "downvote_users",
    joinColumns = @JoinColumn(name = "feeditem_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> downvote;

    public FeedItem() {
    	
    }
    
    
    public FeedItem(Long user_id, String content, String title ) {
		super();
		this.userId = user_id;
		this.content = content;
		this.title = title;
		
		this.time = LocalDateTime.now().toString();
		this.comment = new ArrayList<>();
		this.upvote = new HashSet<>();
		this.downvote = new HashSet<>();
	}


	public Long getFeeditem_id() {
		return feeditem_id;
	}


	public void setFeeditem_id(Long feeditem_id) {
		this.feeditem_id = feeditem_id;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUser_id(Long user_id) {
		this.userId = user_id;
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


	public List<Comment> getComments() {
		return comment;
	}


	public void setComments(List<Comment> comments) {
		this.comment = comments;
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
		return "FeedItem [feeditem_id=" + feeditem_id + ", userId=" + userId + ", title=" + title + ", content="
				+ content + ", time=" + time +"]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(content, feeditem_id, time, title, userId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeedItem other = (FeedItem) obj;
		return Objects.equals(content, other.content)
				&& Objects.equals(feeditem_id, other.feeditem_id) && Objects.equals(time, other.time)
				&& Objects.equals(title, other.title) && Objects.equals(userId, other.userId);
	}
    
    
    

}
