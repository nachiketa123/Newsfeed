package com.example.newsfeed.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "MYUSERS")
@Table(name = "MYUSERS")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long user_id;
	private String name;
	private String email;
	private String password;
	
	@ManyToMany
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "USER_FOLLOW",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "followed_user_id", referencedColumnName = "user_id"))
	private List<User> followList;
	
	@ManyToMany(mappedBy = "followList")
	private List<User> followedByList ;
	
	@OneToMany(mappedBy = "user")
	private Set<Comment> commentList;
	
	
//	@JsonBackReference
//	@ManyToMany(mappedBy = "upvote")
//	private Set<FeedItem> likedFeeds;
//	
//	@JsonBackReference
//	@ManyToMany(mappedBy = "downvote")
//	private Set<FeedItem> dislikedFeeds;
	

	public User(String name, String email, String password) {

		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public User() {

	}
	
	public List<User> getFollow() {
		return followList;
	}

	public void setFollow(List<User> follow) {
		this.followList = follow;
	}

	public List<User> getFollowedByList() {
		return followedByList;
	}

	public void setFollowedByList(List<User> followedByList) {
		this.followedByList = followedByList;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", email=" + email + ", password=" + password
				+ "]";
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

//	public Set<FeedItem> getLikedFeeds() {
//		return likedFeeds;
//	}
//
//	public void setLikedFeeds(Set<FeedItem> likedFeeds) {
//		this.likedFeeds = likedFeeds;
//	}
//	
//	
//
//	public Set<FeedItem> getDislikedFeeds() {
//		return dislikedFeeds;
//	}
//
//	public void setDislikedFeeds(Set<FeedItem> dislikedFeeds) {
//		this.dislikedFeeds = dislikedFeeds;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, password, user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(user_id, other.user_id);
	}
	
	

}
