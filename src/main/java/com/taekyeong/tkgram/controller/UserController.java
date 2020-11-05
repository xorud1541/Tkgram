package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.UserInfoResponseDto;
import com.taekyeong.tkgram.dto.UserLoginRequestDto;
import com.taekyeong.tkgram.service.UserInfoService;
import com.taekyeong.tkgram.service.UserJoinService;
import com.taekyeong.tkgram.service.UserLoginService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import com.taekyeong.tkgram.dto.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserJoinService userJoinService;
    private final UserLoginService userLoginService;
    private final UserInfoService userInfoService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/api/v1/join")
    public HttpStatus joinNewUser(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        Long userindex = userJoinService.saveUser(userJoinRequestDto);
        if(userindex > 0)
            return HttpStatus.OK;
        else
            return HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String token = userLoginService.login(userLoginRequestDto);
        if(token == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        else
            return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/api/v1/my")
    public ResponseEntity getMyInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");

        return ResponseEntity.status(HttpStatus.OK)
                .body(userInfoService.getUserInfo(token.substring("Bearer ".length())));
    }
}
