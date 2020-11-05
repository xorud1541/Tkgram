package com.taekyeong.tkgram.service.user;

import com.taekyeong.tkgram.dto.user.UserInfoResponseDto;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponseDto getUserInfo(String token) {
        Long userindx = jwtTokenProvider.getUserindex(token);
        User user = userRepository.findById(userindx).get();

        return UserInfoResponseDto.builder()
                .email(user.getEmail())
                .userindex(user.getUserindex())
                .username(user.getUsername())
                .build();
    }
}