package com.taekyeong.tkgram.dto.post;

import com.taekyeong.tkgram.entity.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private List<Photo> photoList;
    private Long poster;
    private Long likes;
    private String description;

    @Builder
    public PostResponseDto(List<Photo> photoList, Long poster, Long likes, String description) {
        this.photoList = photoList;
        this.poster = poster;
        this.likes = likes;
        this.description = description;
    }


}
