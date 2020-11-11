package com.taekyeong.tkgram.service.user;

import com.taekyeong.tkgram.dto.UserDto;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserJoinService {
    private final UserRepository userRepository;

    @Transactional
    public Long saveUser(UserDto.RequestJoinUser requestJoinUser) {
        // 사용자 정보에 대한 유효성 검사
        List<User> user = userRepository.findByEmailAndPassword(requestJoinUser.getEmail(), requestJoinUser.getPassword());
        if(user.size() > 0)
            return 0L;

        return userRepository.save(requestJoinUser.toEntity()).getUser();
    }
}
