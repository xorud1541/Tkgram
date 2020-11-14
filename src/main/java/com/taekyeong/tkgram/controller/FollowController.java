package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.service.follow.FollowerService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class FollowController {
    private final JwtTokenProvider jwtTokenProvider;
    private final FollowerService followService;

    @PutMapping("/api/v1/follow/{id}")
    public HttpStatus followUser(HttpServletRequest request, @PathVariable("id") Long toUser) {
        String token = request.getHeader("Authorization");
        Long fromUser = jwtTokenProvider.getUserindex(token.substring("Bearer ".length()));
        return followService.followUser(fromUser, toUser);
    }
}
