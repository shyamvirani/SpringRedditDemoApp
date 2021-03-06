package com.reddit.redditapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.reddit.redditapp.config.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class RedditDemoAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RedditDemoAppApplication.class, args);
	}
	

}
