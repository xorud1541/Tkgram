package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.user.request.UserLoginRequestDto;
import com.taekyeong.tkgram.dto.user.request.UserPutInfoRequestDto;
import com.taekyeong.tkgram.service.user.UserInfoService;
import com.taekyeong.tkgram.service.user.UserJoinService;
import com.taekyeong.tkgram.service.user.UserLoginService;
import com.taekyeong.tkgram.dto.user.request.UserJoinRequestDto;
import com.taekyeong.tkgram.service.user.UserPutInfoService;
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
    private final UserPutInfoService userPutInfoService;

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

    @ApiOperation(value = "내 정보 수정하기", notes = "내 정보를 수정합니다.")
    @PutMapping("/api/v1/user/my")
    public HttpStatus putMyInfo(HttpServletRequest request, @RequestBody UserPutInfoRequestDto userPutInfoRequestDto) {
        String token = request.getHeader("Authorization");
        if(token == null)
            return HttpStatus.NOT_ACCEPTABLE;
        else {
            return userPutInfoService.putUserInfo(token.substring("Bearer ".length()), userPutInfoRequestDto);
        }
    }

}
