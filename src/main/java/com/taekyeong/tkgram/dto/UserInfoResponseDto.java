package com.taekyeong.tkgram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
    private Long userindex;
    private String email;
    private String username;

    // 추후에 추가될 필드
    private List<Long> followers; // 팔로워
    private List<Long> followees; // 팔로이
    private String profileUrl; // 프로필 사진

    private List<Long> posts; // 게시물 정보

    @Builder
    public UserInfoResponseDto(Long userindex, String email, String username) {
        this.userindex = userindex;
        this.email = email;
        this.username = username;
    }

}
