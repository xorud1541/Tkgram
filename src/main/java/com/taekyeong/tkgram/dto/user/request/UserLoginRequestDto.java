package com.taekyeong.tkgram.dto.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {
    private String email;
    private String password;
    private String secretkey;

    @Builder
    public UserLoginRequestDto(String email, String password, String secretkey) {
        this.email = email;
        this.password = password;
        this.secretkey = secretkey;
    }
}
