package com.taekyeong.tkgram.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long photoid;

    @Column
    private String url;

    @Builder
    public Photo(Long post, String url) {
        this.url = url;
    }
}
