package com.taekyeong.tkgram.service.post;

import com.taekyeong.tkgram.dto.PostDto;
import com.taekyeong.tkgram.dto.PhotoDto;
import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.PostRepository;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.Base64ToMultipartFile;
import com.taekyeong.tkgram.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
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
    public Long savePost(PostDto.RequestAddPost requestAddPost, Long userIdx) {
        List<String> photos = requestAddPost.getPhotos();
        if(photos.size() != 1)
            return 0L;

        try {
            // 이미지 업로드
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(photos.get(0).getBytes());
            Base64ToMultipartFile multipartFile = new Base64ToMultipartFile(bytes);

            String url = s3Uploader.uploadImage(multipartFile);
            if(!url.isEmpty()) {
                Optional<User> optional = userRepository.findById(userIdx);
                if(optional.isPresent()) {
                    Photo photo = photoService.postPhoto(PhotoDto.builder().url(url).build());

                    // 게시물 업로드
                    PostDto postDto = new PostDto();
                    postDto.setDescription(requestAddPost.getDescription());
                    postDto.setPoster(optional.get());
                    postDto.setCreatedTime(Instant.now().getEpochSecond());

                    List<Photo> photoList = new ArrayList<>();
                    photoList.add(photo);
                    postDto.setPhotos(photoList);

                    return postRepository.save(postDto.toEntity()).getPost();

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

    public PostDto.ResponsePostInfo getPost(Long idx) {
        Post post = postRepository.findById(idx).get();
        return PostDto.ResponsePostInfo.builder()
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
