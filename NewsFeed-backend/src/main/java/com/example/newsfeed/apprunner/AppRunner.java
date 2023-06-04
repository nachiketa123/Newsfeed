package com.example.newsfeed.apprunner;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.newsfeed.models.FeedItem;
import com.example.newsfeed.service.AccountManager;
import com.example.newsfeed.service.NewsFeedService;
/*
@Component
public class AppRunner implements CommandLineRunner {
	
	@Autowired
    AccountManager accountManager;
	
//	@Autowired
//	UserService userService;
	
	@Autowired
	NewsFeedService newsFeedService;
	
	private int session;
	private boolean logout;
	private Scanner sc;
	
	public AppRunner(AccountManager accountManager){
        this.accountManager = accountManager;
        sc = new Scanner(System.in);
        session = 0;
        logout = true;
    }
	
	public void showMenu() {
		int ch;
	     
	     do {
	    	 System.out.println("----------------MENU----------------");
	    	 System.out.println("1. follow User");
	    	 System.out.println("2. create new post");
	    	 System.out.println("3. add comment to a post");
	    	 System.out.println("4. upvote a post");
	    	 System.out.println("5. downvote a post");
	    	 System.out.println("6. show my news feed");
	    	 System.out.println("10. Logout user");
	    	 System.out.println("Enter your choice:");
	    	 ch = sc.nextInt();
	    	 sc.nextLine();
	    	 boolean res;
	    	 switch(ch) {
	    	 
	    	 case 1:
	    		 System.out.println("Enter Email of the user you want to follow:");
	    		 String email = sc.nextLine();
	    		 res = userService.followUser(email);
	    		 System.out.println(res);
	    		 break;
	    		 
	    	 case 2:
	    		 System.out.println("Enter Content of the post");
	    		 String content = sc.nextLine();
	    		 res = newsFeedService.createFeed(content);
	    		 System.out.println(res);
	    		 break;
	    	
	    	 case 3:
	    		 System.out.println("Enter the Feed Id");
	    		 Long feedId = sc.nextLong();
	    		 sc.nextLine();
	    		 System.out.println("Enter the comment");
	    		 String comment = sc.nextLine();
	    		 res = newsFeedService.addComment(feedId, comment);
	    		 System.out.println(res);
	    		 break;
	    	
	    	 case 4:
	    		 System.out.println("Enter Id of the post");
	    		 Long feedId1 = sc.nextLong();
	    		 newsFeedService.upvote(feedId1);
	    		 break;
	    		 
	    	 case 5:
	    		 System.out.println("Enter Id of the post");
	    		 Long feedId2 = sc.nextLong();
	    		 newsFeedService.downVote(feedId2);
	    		 break;
	    	
	    	 case 6:
	    		 List<FeedItem> feedList =  newsFeedService.getNewsFeed();
	    		 for(FeedItem x: feedList) {
	    			 System.out.println(x.getContent());
	    		 }
	    		 break;
	    	
	    	
	    	 case 10:
	    		 logout = true;
	    		 session--;
	    		 break;
	    	default:
	 
	    	 }
	     }while(!logout);
	     
	     System.out.println("Successfully logged out of the application");
	     System.out.println("Login Again");
	     gotoLoginPage();
	}
	
	public void gotoLoginPage() {
		
        System.out.print("Enter email:");
        String Enteremail = sc.nextLine();
        sc.nextLine();
        System.out.print("Enter password:");
        String password = sc.nextLine();
        
        boolean check = accountManager.login(Enteremail,password);
	    if(check) {
	    	System.out.println("Login successful");
	    	session++;
	    	logout = false;
	    	showMenu();
	    }
	    	
	    else
	    	System.out.println("Login Failed");

	}
	
	@Override
	public void run(String... args) throws Exception {
//	     boolean check  = accountManager.signUp("ayush" , "ayush@test.com" ,"ayush123");
//	     check  = accountManager.signUp("nachiketa" , "nachiketa@test.com" ,"nachiketa123");
//	     check  = accountManager.signUp("luv" , "luv@test.com" ,"luv123");
	     
//	     while(session == 0) {
//	    	 gotoLoginPage();
//	     }
	    
	}

}*/
