package com.taekyeong.tkgram.service.feed;

import com.taekyeong.tkgram.dto.feed.FeedResponseDto;
import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.object.FeedPost;
import com.taekyeong.tkgram.repository.PostRepository;
import com.taekyeong.tkgram.repository.UserRepository;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public FeedResponseDto getFeed(Long userIdx) {
        User user = userRepository.findById(userIdx).get();
        List<FeedPost> feedPosts = new ArrayList<>();
        List<Post> posts = postRepository.findByPoster(userIdx);
        for(Post post : posts)
            feedPosts.add(new FeedPost(post.getPostindex(), post.getPhotos()));

        return FeedResponseDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .posts(feedPosts)
                .build();
    }
}
