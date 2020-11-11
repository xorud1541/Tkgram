package com.taekyeong.tkgram.dto.user.request;

import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPutInfoRequestDto {
    private String username;
    private String profile_edit_type;
    private String profile_image_base64;

    private String profileUrl;
    private String email;
    private String password;

    @Builder
    public UserPutInfoRequestDto(String username, String profile_edit_type, String profile_image_base64) {
        this.username = username;
        this.profile_edit_type = profile_edit_type;
        this.profile_image_base64 = profile_image_base64;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .profile(profileUrl)
                .build();
    }
}
