package com.reddit.redditapp.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SubredditDto {
    private Long subredditId;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private Instant createdDate;
    private Long userId;
}
