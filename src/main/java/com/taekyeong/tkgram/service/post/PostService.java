package com.taekyeong.tkgram.service.post;

import com.taekyeong.tkgram.dto.post.PhotoDto;
import com.taekyeong.tkgram.dto.post.PostRequestDto;
import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.repository.PostRepository;
import com.example.springboot.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;
    private final PhotoService photoService;

    @Transactional
    public Long post(PostRequestDto postRequestDto) {
        List<MultipartFile> images = postRequestDto.getImages();
        if(images.isEmpty())
            return 0L;

        try {
            // 이미지 업로드
            String url = s3Uploader.uploadImage(images.get(0));
            if(!url.isEmpty()) {

                // 업로드한 이미지 경로
                Photo photo = photoService.postPhoto(PhotoDto.builder().url(url).build());
                postRequestDto.getImageUrls().add(photo);

                // 게시물 업로드
                return postRepository.save(postRequestDto.toEntity()).getPostindex();
            }
            else {
                return 0L;
            }
        } catch (IOException e) {
            e.printStackTrace();

            return 0L;
        }
    }
}
