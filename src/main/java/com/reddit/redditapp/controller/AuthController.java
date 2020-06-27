package com.reddit.redditapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.redditapp.dto.AuthenticationResponse;
import com.reddit.redditapp.dto.LoginRequest;
import com.reddit.redditapp.dto.RefreshTokenRequest;
import com.reddit.redditapp.dto.RegisterRequest;
import com.reddit.redditapp.service.AuthService;
import com.reddit.redditapp.service.RefreshTokenService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
	@Autowired
	private final AuthService authService;
	@Autowired
	private final RefreshTokenService refreshTokenService;

	
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
	authService.verifyAccount(token);
	return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
	}
	
	@PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
		return authService.login(loginRequest);
		
	}
	@PostMapping("/refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
		
		return authService.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
	}
	

}
