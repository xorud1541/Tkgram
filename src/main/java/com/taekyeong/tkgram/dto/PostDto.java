package com.taekyeong.tkgram.dto;

import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {

    private String description;
    private User poster;
    private Long createdTime;
    private String thumbnail;
    private List<Photo> photos;

    @Builder PostDto(String description, User poster, Long createdTime, List<Photo> photos, String thumbnail) {
        this.description = description;
        this.poster = poster;
        this.createdTime = createdTime;
        this.photos = photos;
        this.thumbnail = thumbnail;
    }

    public Post toEntity() {
        return Post.builder()
                .description(description)
                .poster(poster)
                .createdTime(createdTime)
                .thumbnail(thumbnail)
                .photos(photos)
                .build();
    }

    // Post Request
    @Setter
    @Getter
    @NoArgsConstructor
    public static class RequestAddPost {
        private List<String> photos;
        private String description;

        @Builder
        public RequestAddPost(List<String> photos, String description) {
            this.photos = photos;
            this.description = description;
        }
    }

    // Post Response
    @Setter
    @Getter
    @NoArgsConstructor
    public static class ResponsePostInfo {
        private List<Photo> photos;
        private Long poster;
        private String description;
        private String thumbnail;

        @Builder
        public ResponsePostInfo(List<Photo> photos, Long poster, String description, String thumbnail) {
            this.photos = photos;
            this.poster = poster;
            this.description = description;
            this.thumbnail = thumbnail;
        }
    }
}
