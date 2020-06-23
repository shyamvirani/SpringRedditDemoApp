package com.reddit.redditapp.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.redditapp.dto.AuthenticationResponse;
import com.reddit.redditapp.dto.LoginRequest;
import com.reddit.redditapp.dto.RegisterRequest;
import com.reddit.redditapp.exceptions.RedditException;
import com.reddit.redditapp.model.NotificationEmail;
import com.reddit.redditapp.model.User;

import com.reddit.redditapp.model.VerificationToken;
import com.reddit.redditapp.repository.UserRepository;
import com.reddit.redditapp.repository.VerificationTokenRepository;
import com.reddit.redditapp.security.JwtProvider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.time.Instant.now;

import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AuthService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setCreated(now());
		user.setEnabled(false);
		userRepo.save(user);
	    
		String token = generateVerificationToken(user);
		 mailService.sendMail(new NotificationEmail("Please Activate your Account",
	                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
	                "please click on the below url to activate your account : " +
	                "http://localhost:8080/api/auth/accountVerification/" + token));
	}
	
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken =new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		
		
		return token;
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		String authenticationToken=jwtProvider.generateToken(authenticate);
		
		return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
	}
	
	



	private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
	
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
		verificationTokenOptional.orElseThrow(() -> new RedditException("Invalid Token"));
		fetchUserAndEnable(verificationTokenOptional.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new RedditException("User Not Found with id - " + username));
		user.setEnabled(true);
		userRepo.save(user);
	}

	@Transactional(readOnly = true)
	User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userRepo.findByUsername(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}
	

}
