package com.reddit.redditapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.redditapp.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
	  Optional<VerificationToken> findByToken(String token);

}
