package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.post.PostRequestDto;
import com.taekyeong.tkgram.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/post")
    public ResponseEntity post(@RequestBody PostRequestDto postRequestDto) {
        Long postNum = postService.post(postRequestDto);
        if(postNum > 0)
            return ResponseEntity.status(HttpStatus.OK).body(postNum);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }
}
