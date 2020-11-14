package com.taekyeong.tkgram.dto;

import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.object.FeedItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UserDto {

    // User Request

    @Setter
    @Getter
    @NoArgsConstructor
    public static class RequestJoinUser {
        private String email;
        private String username;
        private String password;

        @Builder
        public RequestJoinUser (String email, String username, String password) {
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

    @Setter
    @Getter
    @NoArgsConstructor
    public static class RequestLoginUser {
        private String email;
        private String password;
        private String secretKey;

        @Builder
        public RequestLoginUser(String email, String password, String secretKey) {
            this.email = email;
            this.password = password;
            this.secretKey = secretKey;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class RequestPutUserInfo {
        private String username;
        private String profile_edit_type;
        private String profile_image_base64;

        @Builder
        public RequestPutUserInfo(String username, String profile_edit_type, String profile_image_base64) {
            this.username = username;
            this.profile_edit_type = profile_edit_type;
            this.profile_image_base64 = profile_image_base64;
        }
    }


    // User Response

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseUserInfo {
        private Long user;
        private String email;
        private String username;
        private String profile;
        private int followersCnt;
        private int followeesCnt;
        private int relation;

        @Builder
        public ResponseUserInfo(Long user, String email, String username, String profile, List<Post> posts,
                                int followersCnt, int followeesCnt, int relation) {
            this.user = user;
            this.email = email;
            this.username = username;
            this.profile = profile;
            this.followersCnt = followersCnt;
            this.followeesCnt = followeesCnt;
            this.relation = relation;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseLoginUser {
        private Long user;
        private String token;
        private Long expireTime;

        @Builder
        public ResponseLoginUser(Long user, String token, Long expireTime) {
            this.user = user;
            this.token = token;
            this.expireTime = expireTime;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponseFeedInfo {
        private List<FeedItem> feedItems = new ArrayList<>();

        @Builder
        public ResponseFeedInfo(List<FeedItem> feedItems) {
            this.feedItems = feedItems;
        }
    }
}
