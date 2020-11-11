package com.taekyeong.tkgram.service.user;

import com.taekyeong.tkgram.dto.UserDto;
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
    public UserDto.ResponseUserInfo getUserInfo(String token) {
        Long userIdx = jwtTokenProvider.getUserindex(token);
        Optional<User> optional = userRepository.findById(userIdx);

        if(optional.isPresent()) {
            User user = optional.get();
            return UserDto.ResponseUserInfo.builder()
                    .user(user.getUser())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .profile(user.getProfile())
                    .build();
        }
        else {
            return UserDto.ResponseUserInfo.builder()
                    .build();
        }
    }
}