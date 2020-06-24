package com.reddit.redditapp.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.redditapp.dto.PostRequest;
import com.reddit.redditapp.dto.PostResponse;
import com.reddit.redditapp.exceptions.PostNotFoundException;
import com.reddit.redditapp.exceptions.SubredditnotFoundException;
import com.reddit.redditapp.mapper.PostMapper;
import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.Subreddit;
import com.reddit.redditapp.model.User;
import com.reddit.redditapp.repository.PostRepository;
import com.reddit.redditapp.repository.SubredditRepository;
import com.reddit.redditapp.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
	
	private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
	
	public void save(PostRequest postRequest){
		Subreddit subreddit=subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(()-> new SubredditnotFoundException(postRequest.getSubredditName()));
		
		postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
		
	}
	
	public PostResponse getPost(Long id) {
		 Post post = postRepository.findById(id)
	                .orElseThrow(() -> new PostNotFoundException(id.toString()));
	        return postMapper.mapToDto(post);
	}
	   @Transactional(readOnly = true)
	    public List<PostResponse> getAllPosts() {
	        return postRepository.findAll()
	                .stream()
	                .map(postMapper::mapToDto)
	                .collect(toList());
	    }
	   

	    @Transactional(readOnly = true)
	    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
	        Subreddit subreddit = subredditRepository.findById(subredditId)
	                .orElseThrow(() -> new SubredditnotFoundException(subredditId.toString()));
	        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
	        return posts.stream().map(postMapper::mapToDto).collect(toList());
	    }

	    @Transactional(readOnly = true)
	    public List<PostResponse> getPostsByUsername(String username) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException(username));
	        return postRepository.findByUser(user)
	                .stream()
	                .map(postMapper::mapToDto)
	                .collect(toList());
	    }
	
	
	

}
