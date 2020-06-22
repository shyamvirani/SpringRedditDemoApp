package com.reddit.redditapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.Subreddit;
import com.reddit.redditapp.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	
		List<Post>	findAllBySubreddit(Subreddit subreddit);
		List<Post> findByUser(User user);
	

}
