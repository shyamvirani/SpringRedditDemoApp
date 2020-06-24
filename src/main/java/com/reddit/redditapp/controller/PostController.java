package com.reddit.redditapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.redditapp.dto.PostRequest;
import com.reddit.redditapp.dto.PostResponse;
import com.reddit.redditapp.service.PostService;

import static org.springframework.http.ResponseEntity.status;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/posts")
@AllArgsConstructor
public class PostController {
	
	public PostService postService;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
		postService.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts(){
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
		return status(HttpStatus.OK).body(postService.getPost(id));
		
	}
	
	@GetMapping("/bysubreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id){
		return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
		
	}
	
	@GetMapping("/byusername/{username}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username){
		return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
		
	}
	
	
	
	
}
