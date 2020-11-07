package com.taekyeong.tkgram.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
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
    private Long createdTime;

    @Column
    private String description;

    @OneToMany
    @JoinColumn
    private List<Photo> photos;

    @Builder
    public Post(Long poster, String description, List<Photo> photos, Long createdTime) {
        this.poster = poster;
        this.description = description;
        this.photos = photos;
        this.createdTime = createdTime;
    }
}
