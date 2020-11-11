package com.taekyeong.tkgram.service.user;

import com.taekyeong.tkgram.dto.user.response.UserInfoResponseDto;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponseDto getUserInfo(String token) {
        Long userIdx = jwtTokenProvider.getUserindex(token);
        Optional<User> optional = userRepository.findById(userIdx);

        if(optional.isPresent()) {
            User user = optional.get();
            return UserInfoResponseDto.builder()
                    .user(user.getUser())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .profile(user.getProfile())
                    .posts(user.getPosts())
                    .build();
        }
        else {
            return UserInfoResponseDto.builder()
                    .build();
        }
    }
}