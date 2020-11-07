package com.taekyeong.tkgram.object;

import com.taekyeong.tkgram.entity.Photo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FeedPost {
    Long post;
    List<Photo> photos;

    public FeedPost(Long post, List<Photo> photos) {
        this.post = post;
        this.photos = photos;
    }
}
