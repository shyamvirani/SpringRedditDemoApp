package com.reddit.redditapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.redditapp.model.Comment;
import com.reddit.redditapp.model.Post;
import com.reddit.redditapp.model.User;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);

}
