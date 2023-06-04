package com.example.newsfeed;

import com.example.newsfeed.service.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsFeedApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(NewsFeedApplication.class, args);
    }

}
