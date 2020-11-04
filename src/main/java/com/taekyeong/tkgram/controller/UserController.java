package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.service.UserJoinService;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import com.taekyeong.tkgram.dto.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
/*
    @Autowired(required = true)
    private JwtTokenProvider jwtTokenProvider;
*/
    private final UserJoinService userJoinService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/api/v1/join")
    public Long joinNewUser(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        return userJoinService.saveUser(userJoinRequestDto);
    }
/*
    @PostMapping(value = "/login", consumes = "application/json")
    public @ResponseStatus Integer login(@RequestBody UserDto userDto) {

        if(userDto.getEmail().isEmpty())
            return 400;

        if(userDto.getPassword().isEmpty())
            return 400;

        if(userRepository.findByEmail(userDto.getEmail()).size() == 1) {
            String token = jwtTokenProvider.createToken(userDto.getEmail());
            System.out.println(jwtTokenProvider.getSubject(token));
            return 200;
        }
        else {
            return 413;
        }
    }
 */
}
