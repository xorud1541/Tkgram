package com.taekyeong.tkgram.service.post;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.taekyeong.tkgram.dto.post.PhotoDto;
import com.taekyeong.tkgram.dto.post.PostRequestDto;
import com.taekyeong.tkgram.dto.post.PostResponseDto;
import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.PostRepository;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long savePost(PostRequestDto postRequestDto, Long userIdx) {
        List<MultipartFile> images = postRequestDto.getImages();
        if(images.size() != 1)
            return 0L;

        try {
            // 이미지 업로드
            String url = s3Uploader.uploadImage(images.get(0));
            if(!url.isEmpty()) {

                // 업로드한 이미지 경로
                Photo photo = photoService.postPhoto(PhotoDto.builder().url(url).build());
                postRequestDto.getPhotos().add(photo);
                postRequestDto.setCreatedTime(Instant.now().getEpochSecond());

                Optional<User> optional = userRepository.findById(userIdx);
                if(optional.isPresent()) {
                    postRequestDto.setPoster(optional.get());
                    // 게시물 업로드
                    return postRepository.save(postRequestDto.toEntity()).getPost();
                }
                else
                    return 0L;
            }
            else {
                return 0L;
            }
        } catch (IOException e) {
            e.printStackTrace();

            return 0L;
        }
    }

    public PostResponseDto getPost(Long idx) {
        Post post = postRepository.findById(idx).get();
        return PostResponseDto.builder()
                .photos(post.getPhotos())
                .poster(post.getPoster().getUser())
                .description(post.getDescription())
                .build();
    }

    public void deletePost(Long idx) {
        postRepository.deleteById(idx);
    }

    public boolean putPost(Long idx, Long userIdx, String description) {
        Optional<Post> optional = postRepository.findById(idx);
        if(optional.isPresent()) {
            Post post = optional.get();

            if(!post.getPoster().getUser().equals(userIdx))
                return false;

            post.setDescription(description);
            postRepository.save(post);
            return true;
        }
        else {
            return false;
        }
    }
}
