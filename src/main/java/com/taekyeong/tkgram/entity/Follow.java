package com.taekyeong.tkgram.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long follow;

    @ManyToOne
    @JoinColumn(name = "follower")
    private User from;

    @ManyToOne
    @JoinColumn(name = "followee")
    private User to;

    @Builder
    public Follow(User from, User to) {
        this.from = from;
        this.to = to;
    }
}
