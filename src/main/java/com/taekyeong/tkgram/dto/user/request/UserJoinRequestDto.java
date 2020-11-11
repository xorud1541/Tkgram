package com.taekyeong.tkgram.dto.user.request;

import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {
    private String email;
    private String username;
    private String password;

    @Builder
    public UserJoinRequestDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }
}
