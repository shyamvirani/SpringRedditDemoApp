package com.reddit.redditapp.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.reddit.redditapp.dto.SubredditDto;
import com.reddit.redditapp.model.Subreddit;
import com.reddit.redditapp.model.User;

import com.reddit.redditapp.service.PostService;

@Mapper(componentModel = "spring")
public abstract class SubredditMapper {

	@Autowired
	PostService postService;
	
	
	
	@Mapping(target = "numberOfPosts",expression = "java(mapPosts(subreddit.getSubredditId()))")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "userId", source = "user.userId")
	public abstract SubredditDto mapSubredditToDto(Subreddit subreddit);
	
	/*
	 * default Integer mapPosts(List<Post> numberOfPosts) { return
	 * numberOfPosts.size(); }
	 */
	public Integer mapPosts(Long id){
        return  postService.countPostsBySubreddit(id);
    }
	
	@InheritInverseConfiguration
	@Mapping(target = "posts" , ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	public abstract Subreddit mapDtoToSubreddit(SubredditDto subredditDto,User user);
	
	

}
