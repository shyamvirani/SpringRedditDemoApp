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
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	public void save(CommentDto commentDto) {
		Post post = postRepository.findById(commentDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));

		Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder
				.build(post.getUser().getUsername() + "posted comment on your post" + POST_URL);
		sendCommentNotification(message, post.getUser());
		// log.info(post.getUser().toString());
	     //log.info(authService.getCurrentUser().toString());

	}

	public void sendCommentNotification(String message, User user) {

		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));

	}  

	public List<CommentDto> getAllcommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("post not found with postid "+postId.toString()));
		return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());

	}

	public List<CommentDto> getAllCommentForUser(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("user not found with username" +userName));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(toList());
	}

}
