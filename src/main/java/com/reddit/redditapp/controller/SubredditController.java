package com.reddit.redditapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.redditapp.dto.SubredditDto;
import com.reddit.redditapp.service.SubredditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subreddit")

@AllArgsConstructor

public class SubredditController {

	private final SubredditService subredditService;
	
	@GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());
	}
	
    @GetMapping("/{subredditId}")
    public SubredditDto getSubreddit(@PathVariable Long subredditId) {
        return subredditService.getSubreddit(subredditId);
    }

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));

    }
	
}
