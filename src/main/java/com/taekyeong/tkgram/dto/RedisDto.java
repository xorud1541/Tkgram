package com.taekyeong.tkgram.dto;

import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class RedisDto {
    private String description;
    private Long poster;
    private Long createdTime;
    private String thumbnail;
    private List<Photo> photos;

    @Builder
    RedisDto(String description, Long poster, Long createdTime, List<Photo> photos, String thumbnail) {
        this.description = description;
        this.poster = poster;
        this.createdTime = createdTime;
        this.photos = photos;
        this.thumbnail = thumbnail;
    }
}
