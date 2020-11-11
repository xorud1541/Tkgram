package com.taekyeong.tkgram.service.user;

import com.taekyeong.tkgram.dto.UserDto;
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
    public UserDto.ResponseLoginUser login(UserDto.RequestLoginUser requestLoginUser) {
        // 사용자 정보에 대한 유효성 검사
        String email = requestLoginUser.getEmail();
        String password = requestLoginUser.getPassword();
        String secretkey = requestLoginUser.getSecretKey();

        if(email == null || password == null || secretkey == null)
            return null;

        // 등록된 사용자인지 확인
        List<User> user = userRepository.findByEmailAndPassword(email, password);
        if(user.size() == 1)
        {
            UserDto.ResponseLoginUser responseLoginUser = new UserDto.ResponseLoginUser();
            responseLoginUser.setUser(user.get(0).getUser());
            responseLoginUser.setToken(jwtTokenProvider.createToken(user.get(0).getUser().toString(), secretkey));
            responseLoginUser.setExpireTime(jwtTokenProvider.getExpireTime());

            return responseLoginUser;
        }
        else
        {
            return null;
        }

    }
}
