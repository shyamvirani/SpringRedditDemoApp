package com.reddit.redditapp.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.redditapp.model.User;
import com.reddit.redditapp.repository.UserRepository;

import lombok.AllArgsConstructor;
import static java.util.Collections.singletonList;
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	private UserRepository userRepo;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		
		Optional<User> userOptional =  userRepo.findByUsername(username);
		User user = userOptional
				.orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));
		
		return new org.springframework.security
				.core.userdetails.User(user.getUsername(), 
						user.getPassword(), user.isEnabled(), true, true, true, getAuthorities("USER"));
		
	}
	 private Collection<? extends GrantedAuthority> getAuthorities(String role) {
	        return singletonList(new SimpleGrantedAuthority(role));
	    }
	

}
