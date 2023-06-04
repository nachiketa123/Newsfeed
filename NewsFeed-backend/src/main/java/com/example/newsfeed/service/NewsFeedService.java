package com.example.newsfeed.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.newsfeed.DTO.CommentUserDTO;
import com.example.newsfeed.DTO.FeedItemUser;
import com.example.newsfeed.DTO.UserDTO;
import com.example.newsfeed.customexeception.CustomException;
import com.example.newsfeed.models.Comment;
import com.example.newsfeed.models.FeedItem;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repo.CommentRepo;
import com.example.newsfeed.repo.NewsFeedRepo;
import com.example.newsfeed.repo.UserRepo;

@Service
public class NewsFeedService {
	
	private static final Logger logger = LoggerFactory.getLogger(NewsFeedService.class);
	@Autowired
	NewsFeedRepo newsFeedRepo;

	@Autowired
	AccountManager accountManager;

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	UserRepo userRepo;

	/*
	 * DESC: creating user feedItem. 
	 * STRUCTURE: function(userEmail,title,content):
	 * FeedItemUser
	 */
	public FeedItemUser createFeed(String email, String title, String content) {
		try {
			User curr_user = userRepo.findByEmail(email);
			FeedItem newFeed = new FeedItem(curr_user.getUser_id(), content, title);
			newsFeedRepo.save(newFeed);
			return new FeedItemUser(newFeed, curr_user, curr_user);
		} catch (Exception e) {
			logger.error("createFeed() "+e.toString());
			throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong");
		}

	}

	/*
	 * DESC: Adds a comment passed down by the user.
	 * STRUCTURE:
	 * function(userEmail,FeedId,Comment): Boolean
	 */
	public boolean addComment(Long feedId, String commentText) {
		try {

			FeedItem feedItem = newsFeedRepo.findById(feedId).get();
			Comment comment = new Comment(feedItem, commentText);
			commentRepo.save(comment);

			return true;
		} catch (Exception e) {
			logger.error("createFeed() "+e.toString());
			throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong");
		}
	}

	/*
	 * DESC: upvotes a user feedItem. 
	 * 
	 * STRUCTURE: function(userEmail,FeedId):
	 * Map<String,Set<UserDTO>> {upvote: Set(UserDTO), downvote: Set(UserDTO)}
	 */

	public Map<String, Set<UserDTO>> upvote(String userEmail, Long feed_id) {

		Optional<FeedItem> result = newsFeedRepo.findById(feed_id);
		if (result.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "FeedItem/Post not found"); // post not found

		FeedItem feedItem = result.get();
		User userWhoLiked = userRepo.findByEmail(userEmail);
		Set<User> likedList = feedItem.getUpvote();
		Set<User> dislikedList = feedItem.getDownvote();

		// already liked the post
		if (likedList.contains(userWhoLiked)) {
			throw new CustomException(HttpStatus.FORBIDDEN, "User already liked the post");
		}

		// If user have disliked the post first then remove from disliked list
		if (dislikedList.contains(userWhoLiked)) {
			dislikedList.remove(userWhoLiked);
		}

		likedList.add(userWhoLiked);
		feedItem.setUpvote(likedList);
		feedItem.setDownvote(dislikedList);

		// saving to the database
		newsFeedRepo.save(feedItem);

		Map<String, Set<UserDTO>> response = new HashMap<>();
		response.put("upvote", UserDTO.getUserDTOList(likedList));
		response.put("downvote", UserDTO.getUserDTOList(dislikedList));
		return response;

	}

	/*
	 * DESC: downvotes a user feedItem. 
	 * STRUCTURE: function(userEmail,FeedId):
	 * Map<String,Set<UserDTO>> {upvote: Set(UserDTO), downvote: Set(UserDTO)}
	 */

	public Map<String, Set<UserDTO>> downVote(String userEmail, Long feed_id) {

		Optional<FeedItem> result = newsFeedRepo.findById(feed_id);
		if (result.isEmpty())
			throw new CustomException(HttpStatus.NOT_FOUND, "FeedItem/Post not found"); // post not found

		FeedItem feedItem = result.get();
		User userWhoDisliked = userRepo.findByEmail(userEmail);
		Set<User> likedList = feedItem.getUpvote();
		Set<User> dislikedList = feedItem.getDownvote();

		// already disliked the post
		if (dislikedList.contains(userWhoDisliked)) {
			throw new CustomException(HttpStatus.FORBIDDEN, "User already disliked the post");
		}

		// If user have liked the post first then remove from liked list
		if (likedList.contains(userWhoDisliked)) {
			likedList.remove(userWhoDisliked);
		}

		dislikedList.add(userWhoDisliked);
		feedItem.setUpvote(likedList);
		feedItem.setDownvote(dislikedList);

		// saving to the database
		newsFeedRepo.save(feedItem);

		Map<String, Set<UserDTO>> response = new HashMap<>();
		response.put("upvote", UserDTO.getUserDTOList(likedList));
		response.put("downvote", UserDTO.getUserDTOList(dislikedList));
		return response;
	}

	/*
	 * DESC: Fetch the whole NewsFeed of the user. 
	 * 
	 * STRUCTURE: function(userEmail):
	 * List<FeedItemUser>
	 */

	public List<FeedItemUser> getNewsFeed(String email) {

		try {
			User curr_user = userRepo.findByEmail(email);
			Long userId = curr_user.getUser_id();

			List<FeedItemUser> feedItemUsersList = new ArrayList<>();

			List<FeedItem> feedItemList = newsFeedRepo.findByUserId(userId);

			// adding current users feeds
			feedItemUsersList.addAll(FeedItemUser.addUserToFeedList(feedItemList, curr_user, curr_user));

			// getting current users follow list
			List<User> follow_list = curr_user.getFollow();

			// adding followers feeds
			for (int i = 0; i < follow_list.size(); i++) {

				User user_friend = follow_list.get(i);
				Long userFind = user_friend.getUser_id();

				List<FeedItem> feeds = newsFeedRepo.findByUserId(userFind);
				feedItemUsersList.addAll(FeedItemUser.addUserToFeedList(feeds, user_friend, curr_user));

			}

			// using custom sorting
//      Collections.sort(newsFeedList,new MyCustomSort());

			return feedItemUsersList;
		} catch (Exception e) {
			logger.error("gerNewsFeed() "+e.toString());
			throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}
	
	/*
	 * DESC: Add comment to a user Feed.
	 * STRUCTURE:  function(String userEmail,Long FeedId, String CommentText): CommentUserDTO
	 */
	
	 public CommentUserDTO addCommentToFeedItem(String userEmail, Long feed_id, String commentText) {
		 try {
		 User curr_user = userRepo.findByEmail(userEmail);
		 Optional<FeedItem> resFeedItem = newsFeedRepo.findById(feed_id);
		 if(resFeedItem.isEmpty())
			 throw new CustomException(HttpStatus.NOT_FOUND, "FeedItem/Post not found"); //No such feed item found
		 FeedItem feedItem = resFeedItem.get();
		 
		 Comment newComment = new Comment(commentText, curr_user, feedItem);
		 commentRepo.save(newComment);
		 CommentUserDTO commentUserDTO = new CommentUserDTO(newComment,curr_user);
		 return commentUserDTO;
		 
		 }catch(Exception e) {
			 logger.error("addCommentToFeedItem() "+e.toString());
			throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
		 }
	 }
	 
	 /*
		 * DESC: upvotes a comment by the current user.
		 * STRUCTURE:  function(String userEmail, Long comment_id): Map<String,UserDTO>
		 * 				Map<upvote,userlist>
		 * 				Map<downvote,userlist>
		 */
	 
	 public Map<String,Set<UserDTO>> upvoteComment(String userEmail, Long comment_id){
		 try {
			 Optional<Comment> commentRes = commentRepo.findById(comment_id);
			 
			 if(commentRes.isEmpty())
				 throw new CustomException(HttpStatus.NOT_FOUND, "Comment not found");// No comment found 
			 Comment comment = commentRes.get();
			 
			 User userWhoLiked = userRepo.findByEmail(userEmail);
			 
			 
			 Set<User> upvoteUserList = comment.getUpvote();
			 Set<User> downvoteUserList = comment.getDownvote();
			 
			 if(upvoteUserList.contains(userWhoLiked))
				 throw new CustomException(HttpStatus.FORBIDDEN, "User already liked the comment"); //already upvoted by the user
			 
			 //Removing user from downvote list if earlier user has downvoted
			 if(downvoteUserList.contains(userWhoLiked)) {
				 downvoteUserList.remove(userWhoLiked); 
			 }
			 
			 upvoteUserList.add(userWhoLiked);
			 
			 comment.setUpvote(upvoteUserList);
			 comment.setDownvote(downvoteUserList);
			 commentRepo.save(comment);
			 
			 //creating DTO to send 
			 Map<String,Set<UserDTO>> res = new HashMap();
			 Set<UserDTO> upvoteUserDTOList = UserDTO.getUserDTOList(upvoteUserList);
			 Set<UserDTO> downvoteUserDTOList = UserDTO.getUserDTOList(downvoteUserList);
			 
			 res.put("downvote", downvoteUserDTOList);
			 res.put("upvote", upvoteUserDTOList);
			 
			 return res;
			 
		 }catch(Exception e) {
			 logger.error("addCommentToFeedItem() "+e.toString());
				throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
		 }
	 }
	 
	 /*
		 * DESC: downvotes a comment by the current user.
		 * STRUCTURE:  function(String userEmail, Long comment_id): Map<String,UserDTO>
		 * 				Map<upvote,userlist>
		 * 				Map<downvote,userlist>
		 */
	 
	 public Map<String,Set<UserDTO>> downvoteComment(String userEmail, Long comment_id){
		 try {
			 Optional<Comment> commentRes = commentRepo.findById(comment_id);
			 
			 if(commentRes.isEmpty())
				 throw new CustomException(HttpStatus.NOT_FOUND, "Comment not found");// No comment found 
			 Comment comment = commentRes.get();
			 
			 User userWhoDisliked = userRepo.findByEmail(userEmail);
			 
			 
			 Set<User> upvoteUserList = comment.getUpvote();
			 Set<User> downvoteUserList = comment.getDownvote();
			 
			 if(downvoteUserList.contains(userWhoDisliked))
				 throw new CustomException(HttpStatus.FORBIDDEN, "User already disliked the comment"); //already downvoted by the user
			 
			 //Removing user from upvote list if earlier user has upvoted
			 if(upvoteUserList.contains(userWhoDisliked)) {
				 upvoteUserList.remove(userWhoDisliked); 
			 }
			 
			 downvoteUserList.add(userWhoDisliked);
			 
			 comment.setUpvote(upvoteUserList);
			 comment.setDownvote(downvoteUserList);
			 commentRepo.save(comment);
			 
			 //creating DTO to send 
			 Map<String,Set<UserDTO>> res = new HashMap();
			 Set<UserDTO> upvoteUserDTOList = UserDTO.getUserDTOList(upvoteUserList);
			 Set<UserDTO> downvoteUserDTOList = UserDTO.getUserDTOList(downvoteUserList);
			 
			 res.put("downvote", downvoteUserDTOList);
			 res.put("upvote", upvoteUserDTOList);
			 
			 return res;
			 
		 }catch(Exception e) {
			 logger.error("addCommentToFeedItem() "+e.toString());
			throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
		 }
	 }
}
