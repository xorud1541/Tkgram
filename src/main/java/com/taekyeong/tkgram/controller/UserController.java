package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.user.UserLoginRequestDto;
import com.taekyeong.tkgram.service.user.UserInfoService;
import com.taekyeong.tkgram.service.user.UserJoinService;
import com.taekyeong.tkgram.service.user.UserLoginService;
import com.taekyeong.tkgram.dto.user.UserJoinRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserJoinService userJoinService;
    private final UserLoginService userLoginService;
    private final UserInfoService userInfoService;

    @ApiOperation(value = "회원 가입", notes = "회원 가입을 합니다.")
    @PostMapping("/api/v1/join")
    public HttpStatus joinNewUser(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        Long userindex = userJoinService.saveUser(userJoinRequestDto);
        if(userindex > 0)
            return HttpStatus.OK;
        else
            return HttpStatus.BAD_REQUEST;
    }

    @ApiOperation(value = "로그인", notes = "로그인을 시도합니다.")
    @PostMapping("/api/v1/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String token = userLoginService.login(userLoginRequestDto);
        if(token == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        else
            return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @ApiOperation(value = "내 정보보기", notes = "내 정보를 조회합니다.")
    @GetMapping("/api/v1/user/my")
    public ResponseEntity getMyInfo(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if(token == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");

        return ResponseEntity.status(HttpStatus.OK)
                .body(userInfoService.getUserInfo(token.substring("Bearer ".length())));
    }

}
