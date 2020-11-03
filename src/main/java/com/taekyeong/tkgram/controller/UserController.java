package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.util.JwtTokenProvider;
import com.taekyeong.tkgram.model.User;
import com.taekyeong.tkgram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired(required = true)
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/join", consumes = "application/json")
    public @ResponseStatus Integer joinNewUser(@RequestBody User user) {
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();

        if(email.length() == 0 || username.length() == 0 || password.length() == 0)
            return 400;

        User newUser = new User();
        newUser.setUserindex(0);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);

        return 200;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public @ResponseStatus Integer login(@RequestBody User user) {

        if(user.getEmail().isEmpty())
            return 400;

        if(user.getPassword().isEmpty())
            return 400;

        if(userRepository.findByEmail(user.getEmail()).size() == 1) {
            String token = jwtTokenProvider.createToken(user.getEmail());
            System.out.println(jwtTokenProvider.getSubject(token));
            return 200;
        }
        else {
            return 413;
        }
    }
}
