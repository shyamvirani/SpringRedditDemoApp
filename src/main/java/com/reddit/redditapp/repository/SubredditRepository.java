package com.reddit.redditapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.redditapp.model.Subreddit;
import com.reddit.redditapp.model.VerificationToken;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
	
	Optional<Subreddit> findByName(String subredditName);
	

}
