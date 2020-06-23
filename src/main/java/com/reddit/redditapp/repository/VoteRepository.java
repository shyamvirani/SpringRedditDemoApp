package com.reddit.redditapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.User;
import com.reddit.redditapp.model.Vote;
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
