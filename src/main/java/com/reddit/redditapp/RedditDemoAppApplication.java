package com.reddit.redditapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditDemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditDemoAppApplication.class, args);
	}

}
