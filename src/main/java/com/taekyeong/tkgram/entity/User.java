package com.taekyeong.tkgram.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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

    @Column
    private String profile;

    @OneToMany(mappedBy = "poster")
    @OrderBy("createdTime desc")
    private List<Post> posts;

    @OneToMany(mappedBy = "to")
    private List<Follow> followers;

    @OneToMany(mappedBy = "from")
    private List<Follow> followees;

    @Builder
    public User(String email, String username, String password, String profile) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}
