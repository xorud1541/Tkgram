package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.feed.FeedResponseDto;
import com.taekyeong.tkgram.service.feed.FeedService;
import com.taekyeong.tkgram.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/api/v1/feed/{userindex}")
    public FeedResponseDto getFeed(HttpServletRequest request, @PathVariable("userindex") Long userIdx) {
        /*
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.length() == 0)
            return ""; //ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(FeedResponseDto.builder().build());


         */
        return feedService.getFeed(userIdx);
    }

}
