package com.taekyeong.tkgram.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postindex;

    @Column
    private Long poster; // 게시자

    @Column
    private Long likes;

    @Column
    private String description;

    @OneToMany
    @JoinColumn
    private Collection<Photo> photos;

    @Builder
    public Post(Long poster, Long likes, String description, Collection<Photo> photos) {
        this.poster = poster;
        this.likes = likes;
        this.description = description;
        this.photos = photos;
    }
}
