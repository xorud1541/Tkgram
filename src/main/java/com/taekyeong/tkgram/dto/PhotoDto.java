package com.taekyeong.tkgram.dto;

import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoDto {

    private String url;

    @Builder
    public PhotoDto(String url) {
        this.url = url;
    }

    public Photo toEntity() {
        return Photo.builder()
                .url(url)
                .build();
    }
}
