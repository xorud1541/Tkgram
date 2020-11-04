package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.UserLoginRequestDto;
import com.taekyeong.tkgram.service.UserJoinService;
import com.taekyeong.tkgram.service.UserLoginService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import com.taekyeong.tkgram.dto.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
/*
    @Autowired(required = true)
    private JwtTokenProvider jwtTokenProvider;
*/
    private final UserJoinService userJoinService;
    private final UserLoginService userLoginService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/api/v1/join")
    public Long joinNewUser(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        return userJoinService.saveUser(userJoinRequestDto);
    }

    @PostMapping("/api/v1/login")
    public String login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return userLoginService.login(userLoginRequestDto);
    }
}
