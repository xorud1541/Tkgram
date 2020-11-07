package com.taekyeong.tkgram.dto.post;

import com.taekyeong.tkgram.entity.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private List<Photo> photos;
    private Long poster;
    private Long likes;
    private String description;

    @Builder
    public PostResponseDto(List<Photo> photos, Long poster, Long likes, String description) {
        this.photos = photos;
        this.poster = poster;
        this.likes = likes;
        this.description = description;
    }


}
