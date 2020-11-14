package com.taekyeong.tkgram.service.follow;

import com.taekyeong.tkgram.dto.FollowDto;
import com.taekyeong.tkgram.entity.Follow;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.FollowRepository;
import com.taekyeong.tkgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowerService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public HttpStatus followUser(Long fromUser, Long toUser) {
        Optional<User> fromOptional = userRepository.findById(fromUser);
        Optional<User> toOptional = userRepository.findById(toUser);

        if(!fromOptional.isPresent() || !toOptional.isPresent())
            return HttpStatus.BAD_REQUEST;

        User fromUserInfo = fromOptional.get();
        User toUserInfo = toOptional.get();

        Optional<Follow> optional = followRepository.findByFromAndTo(fromUserInfo, toUserInfo);
        if(!optional.isPresent()) {
            followRepository.save(new FollowDto(fromUserInfo, toUserInfo).toEntity());
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
