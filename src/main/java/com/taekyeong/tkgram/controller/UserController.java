package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.UserDto;
import com.taekyeong.tkgram.service.user.UserInfoService;
import com.taekyeong.tkgram.service.user.UserJoinService;
import com.taekyeong.tkgram.service.user.UserLoginService;
import com.taekyeong.tkgram.service.user.UserPutInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final UserPutInfoService userPutInfoService;

    @PostMapping("/api/v1/join")
    public HttpStatus joinNewUser(@RequestBody UserDto.RequestJoinUser requestJoinUser) {
        Long userIdx = userJoinService.saveUser(requestJoinUser);
        if(userIdx > 0)
            return HttpStatus.OK;
        else
            return HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<UserDto.ResponseLoginUser> loginUser(@RequestBody UserDto.RequestLoginUser requestLoginUser) {
        UserDto.ResponseLoginUser responseLoginUser = userLoginService.login(requestLoginUser);
        if(responseLoginUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.ResponseLoginUser.builder().build());
        else {
            return ResponseEntity.status(HttpStatus.OK).body(responseLoginUser);
        }
    }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<UserDto.ResponseUserInfo> getUserInfo(HttpServletRequest request, @PathVariable("id") Long user) {
        String token = request.getHeader("Authorization");

        if(token == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(UserDto.ResponseUserInfo.builder().build());
        else {
            token = token.substring("Bearer ".length());
            return ResponseEntity.status(HttpStatus.OK).body(userInfoService.getUserInfo(token));
        }
    }

    @PutMapping("/api/v1/user/my")
    public HttpStatus putMyInfo(HttpServletRequest request, @RequestBody UserDto.RequestPutUserInfo requestPutUserInfo) {
        String token = request.getHeader("Authorization");
        if(token == null)
            return HttpStatus.NOT_ACCEPTABLE;
        else {
            return userPutInfoService.putUserInfo(token.substring("Bearer ".length()), requestPutUserInfo);
        }
    }
}
