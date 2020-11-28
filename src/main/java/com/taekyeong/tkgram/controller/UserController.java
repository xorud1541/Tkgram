package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.UserDto;
import com.taekyeong.tkgram.service.user.*;
import com.taekyeong.tkgram.util.JwtTokenProvider;
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
    private final UserFeedInfoService userFeedInfoService;
    private final JwtTokenProvider jwtTokenProvider;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else {
            return ResponseEntity.status(HttpStatus.OK).body(responseLoginUser);
        }
    }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<UserDto.ResponseUserInfo> getUserInfo(HttpServletRequest request, @PathVariable("id") Long user) {
        String token = request.getHeader("Authorization");
        if(token == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        else {
            Long myUserIdx = jwtTokenProvider.getUserindex(token.substring("Barear ".length()));
            return ResponseEntity.status(HttpStatus.OK).body(userInfoService.getUserInfo(myUserIdx, user));
        }
    }

    @GetMapping("/api/v1/user/{id}/feed/{count}/{start}")
    public ResponseEntity<UserDto.ResponseFeedInfo> getUserFeedInfo(HttpServletRequest request, @PathVariable("id") Long user,
                                                                    @PathVariable("count") Integer count, @PathVariable("start") Long start) {
        return ResponseEntity.status(HttpStatus.OK).body(userFeedInfoService.getUserFeedInfo(user, count, start));
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
