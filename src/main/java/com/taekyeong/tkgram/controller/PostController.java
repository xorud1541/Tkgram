package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.PostDto;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.service.post.PostService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = {"2. Post"})
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "게시물 등록하기")
    @PostMapping("/api/v1/post")
    public ResponseEntity<?> savePost(HttpServletRequest request, @RequestBody PostDto.RequestAddPost requestAddPost) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.length() == 0)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");

        Long poster = jwtTokenProvider.getUserindex(token);
        Long postNum = postService.savePost(requestAddPost, poster);
        if(postNum > 0)
            return ResponseEntity.status(HttpStatus.OK).body(postNum);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @ApiOperation(value = "게시물 가져오기")
    @GetMapping("/api/v1/post/{id}")
    public PostDto.ResponsePostInfo getPost(@PathVariable("id") Long post) {
        return postService.getPost(post);
    }

    @GetMapping("/api/v1/timeline/{count}/{start}")
    public ResponseEntity<?> getTimeline(HttpServletRequest request,
                                         @PathVariable("count") int count,
                                         @PathVariable("start") int start,
                                         @RequestParam int type) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.length() == 0)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");

        Long user = jwtTokenProvider.getUserindex(token);

        return ResponseEntity.status(HttpStatus.OK).body(postService.getTimeline(user, count, start, type));
    }

    @ApiOperation(value = "게시물 삭제하기")
    @DeleteMapping("/api/v1/post/{id}")
    public HttpStatus deletePost(@PathVariable("id") Long post) {
        postService.deletePost(post);
        return HttpStatus.OK;
    }

    @ApiOperation(value = "게시물 수정하기")
    @PutMapping("/api/v1/post/{id}")
    public HttpStatus putPost(HttpServletRequest request, @PathVariable("id") Long post, @RequestBody Map<String, String> putObject) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.length() == 0)
            return HttpStatus.NOT_ACCEPTABLE;
        else if(putObject.isEmpty() || putObject.get("description").length() == 0)
            return HttpStatus.BAD_REQUEST;
        else {
            Long userIdx = jwtTokenProvider.getUserindex(token);
            if(postService.putPost(post, userIdx, putObject.get("description")))
                return HttpStatus.OK;
            else
                return HttpStatus.BAD_REQUEST;
        }
    }
}
