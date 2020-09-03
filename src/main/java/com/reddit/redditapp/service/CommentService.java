package com.reddit.redditapp.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reddit.redditapp.dto.CommentDto;
import com.reddit.redditapp.exceptions.PostNotFoundException;
import com.reddit.redditapp.mapper.CommentMapper;
import com.reddit.redditapp.model.Comment;
import com.reddit.redditapp.model.NotificationEmail;
import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.User;
import com.reddit.redditapp.repository.CommentRepository;
import com.reddit.redditapp.repository.PostRepository;
import com.reddit.redditapp.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentsRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;

    
    public void save(CommentDto commentDto){
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post id :" + commentDto.getPostId().toString()+ "not found for comment "));

        Comment comments = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentsRepository.save(comments);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }


    public List<CommentDto> getAllCommentsForPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not Found id : " + id.toString()));

        return commentsRepository.findByPost(post)
                .stream()
                .map(commentMapper :: mapToDto)
                .collect(toList());
    }

    public List<CommentDto> getAllCommentsForuser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with name:" + username));
        return commentsRepository.findAllByUser(user)
                .stream()
                .map(commentMapper :: mapToDto)
                .collect(toList());
    }
}
