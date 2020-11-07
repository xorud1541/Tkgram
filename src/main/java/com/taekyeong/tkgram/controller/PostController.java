package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.post.PostRequestDto;
import com.taekyeong.tkgram.dto.post.PostResponseDto;
import com.taekyeong.tkgram.service.post.PostService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/v1/post")
    public ResponseEntity post(@RequestPart("description") String description, @RequestPart("images") List<MultipartFile> images, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.length() == 0)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");

        Long poster = jwtTokenProvider.getUserindex(token);
        PostRequestDto postResponseDto = PostRequestDto.builder().description(description).images(images).build();
        postResponseDto.setPoster(poster);

        Long postNum = postService.post(postResponseDto);
        if(postNum > 0)
            return ResponseEntity.status(HttpStatus.OK).body(postNum);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long postid) {
        return postService.getPost(postid);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public HttpStatus deletePost(@PathVariable("id") Long postid) {
        postService.deletePost(postid);
        return HttpStatus.OK;
    }
}
