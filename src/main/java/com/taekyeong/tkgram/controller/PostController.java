package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.post.PostRequestDto;
import com.taekyeong.tkgram.dto.post.PostResponseDto;
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

    @ApiOperation(value = "게시물 올리기", notes = "게시물을 올립니다.")
    @PostMapping("/api/v1/post")
    public ResponseEntity savePost(@RequestPart("description") String description, @RequestPart("images") List<MultipartFile> images, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.length() == 0)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");

        Long poster = jwtTokenProvider.getUserindex(token);
        PostRequestDto postResponseDto = PostRequestDto.builder().description(description).images(images).build();

        Long postNum = postService.savePost(postResponseDto, poster);
        if(postNum > 0)
            return ResponseEntity.status(HttpStatus.OK).body(postNum);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @ApiOperation(value = "게시물 가져오기", notes = "특정 게시물에 대한 정보를 가져옵니다.")
    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long postid) {
        return postService.getPost(postid);
    }

    @ApiOperation(value = "게시물 삭제", notes = "특정 게시물을 삭제합니다.")
    @DeleteMapping("/api/v1/post/{id}")
    public HttpStatus deletePost(@PathVariable("id") Long postid) {
        postService.deletePost(postid);
        return HttpStatus.OK;
    }

    @ApiOperation(value = "게시물 수정하기", notes = "특정 게시물에 대한 설명을 수정합니다.")
    @PutMapping("/api/v1/post/{id}")
    public HttpStatus putPost(@PathVariable("id") Long postid, @RequestBody Map<String, String> putObject, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.length() == 0)
            return HttpStatus.NOT_ACCEPTABLE;
        else if(putObject.isEmpty() || putObject.get("description").length() == 0)
            return HttpStatus.BAD_REQUEST;
        else {
            Long userIdx = jwtTokenProvider.getUserindex(token);
            if(postService.putPost(postid, userIdx, putObject.get("description")))
                return HttpStatus.OK;
            else
                return HttpStatus.BAD_REQUEST;
        }
    }
}
