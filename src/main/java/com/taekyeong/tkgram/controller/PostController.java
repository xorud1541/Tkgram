package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.PostDto;
import com.taekyeong.tkgram.service.post.PostService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/v1/post")
    public ResponseEntity<Long> savePost(HttpServletRequest request, @RequestBody PostDto.RequestAddPost requestAddPost) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long poster = jwtTokenProvider.getUserIndex(token);
        Long postNum = postService.savePost(requestAddPost, poster);
        if(postNum > 0)
            return ResponseEntity.status(HttpStatus.OK).body(postNum);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/v1/post/{id}")
    public PostDto.ResponsePostInfo getPost(@PathVariable("id") Long post) {
        return postService.getPost(post);
    }

    @GetMapping("/api/v1/timeline/{count}/{start}")
    public ResponseEntity<PostDto.ResponseTimelinePosts> getTimeline(HttpServletRequest request,
                                         @PathVariable("count") int count,
                                         @PathVariable("start") Long start,
                                         @RequestParam int type) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long user = jwtTokenProvider.getUserIndex(token);
        return ResponseEntity.status(HttpStatus.OK).body(postService.getTimeline(user, count, start, type));
    }

    @DeleteMapping("/api/v1/post/{id}")
    public HttpStatus deletePost(@PathVariable("id") Long post) {
        postService.deletePost(post);
        return HttpStatus.OK;
    }

    @PutMapping("/api/v1/post/{id}")
    public HttpStatus putPost(HttpServletRequest request, @PathVariable("id") Long post, @RequestBody Map<String, String> putObject) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(putObject.isEmpty() || putObject.get("description").length() == 0)
            return HttpStatus.BAD_REQUEST;
        else {
            Long userIdx = jwtTokenProvider.getUserIndex(token);
            if(postService.putPost(post, userIdx, putObject.get("description")))
                return HttpStatus.OK;
            else
                return HttpStatus.BAD_REQUEST;
        }
    }
}
