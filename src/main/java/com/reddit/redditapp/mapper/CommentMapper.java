package com.reddit.redditapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.reddit.redditapp.dto.CommentDto;
import com.reddit.redditapp.model.Comment;
import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.User;

@Mapper(componentModel = "spring")

public abstract class CommentMapper {

	
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
	public abstract Comment map(CommentDto commentDto,Post post, User user);
    
    String getDuration(Comment comment) {
        return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
    }
    
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "duration", expression = "java(getDuration(comment))")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    public abstract CommentDto mapToDto(Comment comment);
    
}
