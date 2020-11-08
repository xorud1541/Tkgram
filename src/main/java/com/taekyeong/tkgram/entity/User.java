package com.taekyeong.tkgram.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 10, nullable = true)
    private String username;

    @Column(length = 16, nullable = false)
    private String password;

    @OneToMany(mappedBy = "poster")
    private List<Post> posts;

    @Builder
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
