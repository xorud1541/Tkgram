package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.post.PostRequestDto;
import com.taekyeong.tkgram.dto.post.PostResponseDto;
import com.taekyeong.tkgram.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/post")
    public ResponseEntity post(@RequestPart("description") String description, @RequestPart("images") List<MultipartFile> images) {
        Long postNum = postService.post(PostRequestDto.builder().description(description).images(images).build());
        if(postNum > 0)
            return ResponseEntity.status(HttpStatus.OK).body(postNum);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long postid) {
        return postService.getPost(postid);
    }
}
