package com.taekyeong.tkgram.dto.post;

import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private List<MultipartFile> images;
    private String description;

    private Long poster;
    private List<Photo> photos;
    private Long createdTime;

    @Builder
    public PostRequestDto(List<MultipartFile> images, String description) {
        this.images = images;
        this.description = description;
        this.photos = new ArrayList<Photo>();
    }

    public void setPoster(Long poster) {
        this.poster = poster;
    }

    public void setCreatedTime(Long createdTime) { this.createdTime = createdTime; }

    public Post toEntity() {
        return Post.builder()
                .photos(photos)
                .poster(poster)
                .description(description)
                .createdTime(createdTime)
                .build();
    }
}
