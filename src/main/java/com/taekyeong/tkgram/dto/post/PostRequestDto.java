package com.taekyeong.tkgram.dto.post;

import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private List<MultipartFile> images;
    private String description;

    private Long poster;
    private List<Photo> imageUrls;

    @Builder
    public PostRequestDto(List<MultipartFile> images, String description) {
        this.images = images;
        this.description = description;
    }

    public void setPoster(Long poster) {
        this.poster = poster;
    }

    public Post toEntity() {
        return Post.builder()
                .photos(imageUrls)
                .poster(poster)
                .description(description)
                .likes(0L)
                .build();
    }
}
