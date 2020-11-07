package com.taekyeong.tkgram.object;

import com.taekyeong.tkgram.entity.Photo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FeedPost {
    Long postidx;
    List<Photo> photos;

    public FeedPost(Long postidx, List<Photo> photos) {
        this.postidx = postidx;
        this.photos = photos;
    }
}
