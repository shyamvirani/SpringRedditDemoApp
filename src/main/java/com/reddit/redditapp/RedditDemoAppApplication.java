package com.reddit.redditapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.reddit.redditapp.config.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
@CrossOrigin(origins = "*")
public class RedditDemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditDemoAppApplication.class, args);
	}

}
