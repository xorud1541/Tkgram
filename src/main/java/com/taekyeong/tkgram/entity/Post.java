package com.taekyeong.tkgram.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long post;

    @ManyToOne
    @JoinColumn(name = "user")
    private User poster; // 게시자

    @Column
    private Long createdTime;

    @Column
    private String description;

    @Column
    private String thumbnail;

    @OneToMany
    @JoinColumn
    private List<Photo> photos;

    @Builder
    public Post(User poster, String description, List<Photo> photos, Long createdTime, String thumbnail) {
        this.poster = poster;
        this.description = description;
        this.photos = photos;
        this.createdTime = createdTime;
        this.thumbnail = thumbnail;
    }
}
