package com.taekyeong.tkgram.object;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedItem {
    private Long post;
    private String thumbnail;

    @Builder
    public FeedItem(Long post, String thumbnail) {
        this.post = post;
        this.thumbnail = thumbnail;
    }
}
