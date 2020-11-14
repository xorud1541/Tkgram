package com.taekyeong.tkgram.dto;

import com.taekyeong.tkgram.entity.Follow;
import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowDto {

    private User fromUser;
    private User toUser;

    @Builder
    public FollowDto(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public Follow toEntity() {
        return Follow.builder()
                .from(fromUser)
                .to(toUser)
                .build();
    }
}
