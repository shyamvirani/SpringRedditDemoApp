package com.reddit.redditapp.model;

import java.util.Arrays;

import com.reddit.redditapp.exceptions.RedditException;

public enum VoteType {
	UPVOTE(1),DOWNVOTE(-1);
	
	private int direction;
	
	VoteType(int direction){}
	
	public static VoteType lookup(Integer direction) {
		return Arrays.stream(VoteType.values())
				.filter(value -> value.getDirection().equals(direction))
				.findAny()
				.orElseThrow(() -> new RedditException("vote not found"));
	}
	
	public Integer getDirection() {
		return direction;
	}
	
	
}
