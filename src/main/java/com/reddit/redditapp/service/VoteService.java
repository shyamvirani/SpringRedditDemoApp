package com.reddit.redditapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.redditapp.dto.VoteDto;
import com.reddit.redditapp.exceptions.PostNotFoundException;
import com.reddit.redditapp.exceptions.RedditException;
import com.reddit.redditapp.model.Post;
import static com.reddit.redditapp.model.VoteType.*;

import com.reddit.redditapp.model.Vote;
import com.reddit.redditapp.repository.PostRepository;
import com.reddit.redditapp.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;

	@Transactional
	public void createVote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("post not found with id" + voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
	            throw new RedditException("You have already "
	                    + voteDto.getVoteType() + "' for this post");
	        }
		if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}
	
	
	private Vote mapToVote(VoteDto voteDto,Post post){
		
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
	
	
	
	
	
	
}
