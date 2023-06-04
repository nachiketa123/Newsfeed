package com.example.newsfeed.utils;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.example.newsfeed.models.FeedItem;

public class MyCustomSort implements Comparator<FeedItem> {

	@Override
	public int compare(FeedItem a, FeedItem b) {
		 int x = a.getUpvote() -  a.getDownvote();
	        int y = b.getUpvote() -  b.getDownvote();

	        int comment_a = a.getComments().size();
	        int comment_b = b.getComments().size();


	        LocalDateTime feedTime_a = LocalDateTime.parse(a.getTime());
	        LocalDateTime feedTime_b = LocalDateTime.parse(b.getTime());




	       int result = 0;

	       if(x > y){
	           result = -1;
	       }
	       else if(x < y){
	           result = 1;
	       }
	       else{
	           result = 0;
	       }

	       if(result == 0){
	           if(comment_a > comment_b){
	               result = -1;
	           }
	           else if(comment_a < comment_b){
	               result  = 1;
	           }
	           else{
	               result = 0;
	           }

	       }


	       if(result == 0){
	           int diff = (feedTime_a.compareTo(feedTime_b));

	           if(diff > 0){
	               result = -1;
	           }
	           else if(diff < 0){
	               return 1;
	           }
	           else{
	               return 0;
	           }
	       }


	       return result;
	}
	
}
