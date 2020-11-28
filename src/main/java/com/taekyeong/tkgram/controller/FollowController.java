package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.service.follow.FollowerService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class FollowController {
    private final JwtTokenProvider jwtTokenProvider;
    private final FollowerService followService;

    @PostMapping("/api/v1/follow/{id}")
    public HttpStatus followUser(HttpServletRequest request, @PathVariable("id") Long toUser) {
        String token = request.getHeader("Authorization");
        Long fromUser = jwtTokenProvider.getUserindex(token.substring("Bearer ".length()));
        return followService.followUser(fromUser, toUser);
    }

    @DeleteMapping("/api/v1/follow/{id}")
    public HttpStatus unfollowUser(HttpServletRequest request, @PathVariable("id") Long toUser) {
        String token = request.getHeader("Authorization");
        Long fromUser = jwtTokenProvider.getUserindex(token.substring("Bearer ".length()));
        return followService.unfollowUser(fromUser, toUser);
    }
}
