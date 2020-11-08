package com.taekyeong.tkgram.dto.post;

import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private List<Photo> photos;
    private Long poster;
    private String description;

    @Builder
    public PostResponseDto(List<Photo> photos, Long poster, String description) {
        this.photos = photos;
        this.poster = poster;
        this.description = description;
    }


}
