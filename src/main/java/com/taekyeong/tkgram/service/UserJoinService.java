package com.taekyeong.tkgram.service;

import com.taekyeong.tkgram.dto.UserJoinRequestDto;
import com.taekyeong.tkgram.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserJoinService {
    private final UserRepository userRepository;

    @Transactional
    public Long saveUser(UserJoinRequestDto userJoinRequestDto) {

        // 사용자 정보에 대한 유효성 검사

        return userRepository.save(userJoinRequestDto.toEntity()).getUserindex();
    }
}
