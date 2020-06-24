package com.reddit.redditapp.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reddit.redditapp.dto.SubredditDto;
import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.Subreddit;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	@Mapping(target = "numberOfPosts",expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> numberOfPosts) {
		return numberOfPosts.size();
	}
	
	@InheritInverseConfiguration
	@Mapping(target = "posts" ,ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
	
	

}
