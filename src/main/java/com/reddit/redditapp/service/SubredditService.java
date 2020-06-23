package com.reddit.redditapp.service;

import java.util.List;

import org.hibernate.transform.ToListResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.redditapp.dto.SubredditDto;
import com.reddit.redditapp.exceptions.SubredditnotFoundException;
import com.reddit.redditapp.model.Subreddit;
import com.reddit.redditapp.repository.SubredditRepository;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;
import static java.time.Instant.now;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final AuthService authService;

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream().map(this::mapToDto).collect(toList());
	}

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = subredditRepository.save(mapToSubreddit(subredditDto));
		subredditDto.setId(subreddit.getSubredditId());
		return subredditDto;
	}

	@Transactional(readOnly = true)
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SubredditnotFoundException("Subreddit not found with id -" + id));
		return mapToDto(subreddit);
	}

	private SubredditDto mapToDto(Subreddit subreddit) {
		return SubredditDto.builder().name(subreddit.getName()).id(subreddit.getSubredditId())
				.postCount(subreddit.getPosts().size()).build();
	}

	private Subreddit mapToSubreddit(SubredditDto subredditDto) {
		return Subreddit.builder().name("/r/" + subredditDto.getName()).description(subredditDto.getDescription())
				.user(authService.getCurrentUser()).createdDate(now()).build();
	}
}