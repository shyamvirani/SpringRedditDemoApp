package com.reddit.redditapp.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.reddit.redditapp.dto.SubredditDto;
import com.reddit.redditapp.exceptions.RedditException;
import com.reddit.redditapp.mapper.SubredditMapper;
import com.reddit.redditapp.model.Subreddit;
import com.reddit.redditapp.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j

public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final AuthService authService;
    private final SubredditMapper subredditMapper;

	@Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto,authService.getCurrentUser()));
        subredditDto.setSubredditId(save.getSubredditId());
        return subredditDto;
    }

    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}