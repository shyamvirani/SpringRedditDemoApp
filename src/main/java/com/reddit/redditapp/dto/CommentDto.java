package com.reddit.redditapp.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
	private Long commentId;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String username;
    private String duration;

}
