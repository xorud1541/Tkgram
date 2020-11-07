package com.taekyeong.tkgram.dto.feed;

import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.object.FeedPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FeedResponseDto {
    private String email;
    private String username;
    List<FeedPost> posts;

    @Builder
    public FeedResponseDto(String email, String username, List<FeedPost> posts) {
        this.email = email;
        this.username = username;
        this.posts = posts;
    }
}
