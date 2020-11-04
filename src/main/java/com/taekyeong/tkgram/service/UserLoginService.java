package com.taekyeong.tkgram.service;

import com.taekyeong.tkgram.dto.UserJoinRequestDto;
import com.taekyeong.tkgram.dto.UserLoginRequestDto;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserLoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String login(UserLoginRequestDto userLoginRequestDto) {
        // 사용자 정보에 대한 유효성 검사

        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();
        String secretkey = userLoginRequestDto.getSecretkey();

        // 등록된 사용자인지 확인
        List<User> user = userRepository.findByEmailAndPassword(email, password);
        if(!user.isEmpty())
        {
            // 토큰 발급
            return jwtTokenProvider.createToken(user.get(0).getUserindex().toString(), secretkey);
        }
        else
        {
            return null;
        }

    }
}
