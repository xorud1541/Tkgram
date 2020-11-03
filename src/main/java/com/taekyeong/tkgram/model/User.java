package com.taekyeong.tkgram.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer userindex;

    private String email;
    private String username;
    private String password;

    public void setUserindex(Integer userindex) { this.userindex = userindex; }
    public Integer getUserindex() { return this.userindex; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return this.email; }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return this.username; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return this.password; }
}
