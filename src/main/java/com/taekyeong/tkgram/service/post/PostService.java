package com.taekyeong.tkgram.service.post;

import com.taekyeong.tkgram.dto.PostDto;
import com.taekyeong.tkgram.dto.PhotoDto;
import com.taekyeong.tkgram.entity.Follow;
import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.entity.Post;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.PostRepository;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.Base64ToMultipartFile;
import com.taekyeong.tkgram.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final S3Uploader s3Uploader;
    private final RedisTemplate<String, Number> timeline;

    @Transactional
    public Long savePost(PostDto.RequestAddPost requestAddPost, Long userIdx) {
        List<String> photos = requestAddPost.getPhotos();
        if(photos.isEmpty())
            return 0L;

        try {
            List<MultipartFile> multipartFiles = new ArrayList<>();

            for(String photo : photos) {
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] bytes = decoder.decode(photo.getBytes());
                Base64ToMultipartFile multipartFile = new Base64ToMultipartFile(bytes);
                multipartFiles.add(multipartFile);
            }
            // 이미지 업로드
            List<String> imageUrls = s3Uploader.uploadImages(multipartFiles);
            String thumbnailUrl = s3Uploader.uploadThumbnail(multipartFiles.get(0));
            if(!imageUrls.isEmpty()) {
                Optional<User> optional = userRepository.findById(userIdx);
                if(optional.isPresent()) {
                    PostDto postDto = new PostDto();
                    postDto.setDescription(requestAddPost.getDescription()); // 게시물 설명
                    postDto.setPoster(optional.get()); // 게시자
                    postDto.setCreatedTime(Instant.now().getEpochSecond()); // 게시한 시간

                    List<Photo> photoList = new ArrayList<>(); // 게시물들
                    for(String imageUrl : imageUrls) {
                        Photo photo = photoService.postPhoto(PhotoDto.builder().url(imageUrl).build());
                        photoList.add(photo);
                    }
                    postDto.setPhotos(photoList);
                    postDto.setThumbnail(thumbnailUrl); // 게시물 썸네일 주소

                    Post post = postRepository.save(postDto.toEntity());

                    for(Follow follow : post.getPoster().getFollowers()) {
                        timeline.opsForList().leftPush(
                                String.valueOf(follow.getFrom().getUser()), /* 내 팔로우 */
                                post.getPost() /* 게시글 번호 */
                        );
                    }

                    return post.getPost();
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
                .thumbnail(post.getThumbnail())
                .build();
    }

    @Transactional
    public PostDto.ResponseTimelinePosts getTimeline(Long user, int count, Long start, int type) {
        List<PostDto.TimelinePostInfo> timelinePostInfoList = new ArrayList<>();
        int len = Objects.requireNonNull(timeline.opsForList().size(String.valueOf(user))).intValue();

        if(type == 0) {
            // 현재 리스트
            long idx = 0;
            while (timelinePostInfoList.size() < count && idx < len) {
                Long post = Long.valueOf(Objects.requireNonNull(timeline.opsForList().index(String.valueOf(user.longValue()), idx)).toString());
                Optional<Post> optionalPost = postRepository.findById(post);
                if (optionalPost.isPresent()) {
                    Post postInfo = optionalPost.get();
                    timelinePostInfoList.add(PostDto.TimelinePostInfo.builder()
                            .user(postInfo.getPoster().getUser())
                            .username(postInfo.getPoster().getUsername())
                            .post(post)
                            .createdTime(postInfo.getCreatedTime())
                            .photos(postInfo.getPhotos())
                            .description(postInfo.getDescription())
                            .build());
                }
                idx++;
            }
        }
        else if(type == 1) {
            // 최신 리스트
            for(long idx = 0; idx < len; idx++) {
                if(timelinePostInfoList.size() < count) {
                    Long post = Long.valueOf(Objects.requireNonNull(timeline.opsForList().index(String.valueOf(user.longValue()), idx)).toString());
                    Optional<Post> optionalPost = postRepository.findById(post);
                    if (optionalPost.isPresent()) {
                        if(post.equals(start))
                            break;

                        Post postInfo = optionalPost.get();
                        timelinePostInfoList.add(PostDto.TimelinePostInfo.builder()
                                .user(postInfo.getPoster().getUser())
                                .username(postInfo.getPoster().getUsername())
                                .post(post)
                                .createdTime(postInfo.getCreatedTime())
                                .photos(postInfo.getPhotos())
                                .description(postInfo.getDescription())
                                .build());

                        if(timelinePostInfoList.size() > count)
                            timelinePostInfoList.remove(0);
                    }
                }
            }
        }
        else if(type == 2){
            // 과거 리스트
            for(long idx = 0; idx < len; idx++) {
                Long post = Long.valueOf(Objects.requireNonNull(timeline.opsForList().index(String.valueOf(user.longValue()), idx)).toString());
                if(post > start) {
                    Optional<Post> optionalPost = postRepository.findById(post);
                    if (optionalPost.isPresent()) {
                        Post postInfo = optionalPost.get();
                        timelinePostInfoList.add(PostDto.TimelinePostInfo.builder()
                                .user(postInfo.getPoster().getUser())
                                .username(postInfo.getPoster().getUsername())
                                .post(post)
                                .createdTime(postInfo.getCreatedTime())
                                .photos(postInfo.getPhotos())
                                .description(postInfo.getDescription())
                                .build());
                    }
                }

                if(timelinePostInfoList.size() > count)
                    break;
            }
        }

        return PostDto.ResponseTimelinePosts.builder()
                .timeline(timelinePostInfoList)
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
