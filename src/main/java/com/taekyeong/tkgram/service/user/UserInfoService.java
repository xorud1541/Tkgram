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

import javax.swing.text.html.Option;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserInfoService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public UserDto.ResponseUserInfo getUserInfo(Long myUserIdx, Long userIdx) {

        int relation = -1;
        Optional<User> optionalMyUser = userRepository.findById(myUserIdx);
        Optional<User> optionalUser = userRepository.findById(userIdx);

        if(optionalMyUser.isPresent() && optionalUser.isPresent()) {
            User myUserInfo = optionalMyUser.get();
            User userInfo = optionalUser.get();

            if(myUserInfo.getUser().equals(userInfo.getUser())) {
                relation = 0;
            }
            else {
                Optional<Follow> optional = followRepository.findByFromAndTo(myUserInfo, userInfo);
                if(optional.isPresent()) {
                    relation = 1;
                }
                else {
                    relation = 2;
                }
            }

            return UserDto.ResponseUserInfo.builder()
                    .user(userInfo.getUser())
                    .email(userInfo.getEmail())
                    .username(userInfo.getUsername())
                    .profile(userInfo.getProfile())
                    .followersCnt(userInfo.getFollowers().size())
                    .followeesCnt(userInfo.getFollowees().size())
                    .relation(relation)
                    .build();
        }
        else {
            return UserDto.ResponseUserInfo.builder()
                    .build();
        }
    }

    @Transactional
    public List<UserDto.ResponseUserInfo> getUserFollowerInfos(Long myUserIndex, Long otherUserIndex) {
        List<UserDto.ResponseUserInfo> followerInfoList = new ArrayList<>();
        Optional<User> optionalOtherUserInfo = userRepository.findById(otherUserIndex);
        if(optionalOtherUserInfo.isPresent()) {
            User otherUser = optionalOtherUserInfo.get();
            for(Follow follow : otherUser.getFollowers()) {
                Long followerIndex = follow.getFrom().getUser();
                followerInfoList.add(getUserInfo(myUserIndex, followerIndex));
            }

            return followerInfoList;
        }
        else
            return followerInfoList;
    }

    @Transactional
    public List<UserDto.ResponseUserInfo> getUserFolloweeInfos(Long myUserIndex, Long otherUserIndex) {
        List<UserDto.ResponseUserInfo> followeeInfoList = new ArrayList<>();
        Optional<User> optionalOtherUserInfo = userRepository.findById(otherUserIndex);
        if(optionalOtherUserInfo.isPresent()) {
            User otherUser = optionalOtherUserInfo.get();
            for(Follow follow : otherUser.getFollowees()) {
                Long followeeIndex = follow.getTo().getUser();
                followeeInfoList.add(getUserInfo(myUserIndex, followeeIndex));
            }

            return followeeInfoList;
        }
        else
            return followeeInfoList;
    }
}