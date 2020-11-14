package com.taekyeong.tkgram.service.user;

import com.taekyeong.tkgram.dto.UserDto;
import com.taekyeong.tkgram.entity.Follow;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.FollowRepository;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public UserDto.ResponseUserInfo getUserInfo(Long userIdx) {
        Optional<User> optional = userRepository.findById(userIdx);

        if(optional.isPresent()) {
            User user = optional.get();
            return UserDto.ResponseUserInfo.builder()
                    .user(user.getUser())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .profile(user.getProfile())
                    .followersCnt(user.getFollowers().size())
                    .followeesCnt(user.getFollowees().size())
                    .build();
        }
        else {
            return UserDto.ResponseUserInfo.builder()
                    .build();
        }
    }
}