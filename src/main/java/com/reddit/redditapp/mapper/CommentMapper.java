package com.reddit.redditapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reddit.redditapp.dto.CommentDto;
import com.reddit.redditapp.model.Comment;
import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
	Comment map(CommentDto commentDto,Post post, User user);
    
    
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);
}
