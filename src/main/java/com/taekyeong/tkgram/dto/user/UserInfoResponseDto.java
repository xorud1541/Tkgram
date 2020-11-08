package com.taekyeong.tkgram.dto.user;

import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
    private Long user;
    private String email;
    private String username;
    private List<Post> posts;

    @Builder
    public UserInfoResponseDto(Long user, String email, String username, List<Post> posts) {
        this.user = user;
        this.email = email;
        this.username = username;
        this.posts = posts;
    }

}
