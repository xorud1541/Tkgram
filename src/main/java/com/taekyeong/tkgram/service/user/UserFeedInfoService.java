package com.taekyeong.tkgram.service.user;

import com.taekyeong.tkgram.dto.UserDto;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.object.FeedItem;
import com.taekyeong.tkgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserFeedInfoService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto.ResponseFeedInfo getUserFeedInfo(Long user, Integer count, Long startFeed) {
        Optional<User> optionalUser = userRepository.findById(user);
        if(optionalUser.isPresent()) {
            User userInfo = optionalUser.get();

            UserDto.ResponseFeedInfo responseFeedInfo = new UserDto.ResponseFeedInfo();

            for(Post post : userInfo.getPosts()) {
                FeedItem feedItem = new FeedItem();

                if(startFeed < post.getPost()) {
                    feedItem.setPost(post.getPost());
                    feedItem.setThumbnail(post.getThumbnail());

                    responseFeedInfo.getFeedItems().add(feedItem);
                }

                if(responseFeedInfo.getFeedItems().size() == count)
                    break;
            }

            return responseFeedInfo;
        }
        else
            return UserDto.ResponseFeedInfo.builder().build();
    }
}
