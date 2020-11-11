package com.taekyeong.tkgram.service.user;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.taekyeong.tkgram.dto.UserDto;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.UserRepository;
import com.taekyeong.tkgram.util.Base64ToMultipartFile;
import com.taekyeong.tkgram.util.JwtTokenProvider;
import com.taekyeong.tkgram.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserPutInfoService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public HttpStatus putUserInfo(String token, UserDto.RequestPutUserInfo requestPutUserInfo) {
        Long userIdx = jwtTokenProvider.getUserindex(token);
        Optional<User> optional = userRepository.findById(userIdx);

        if(optional.isPresent())
        {
            User user = optional.get();

            // Put Username
            String currentUsername = user.getUsername();
            String nextUsername = requestPutUserInfo.getUsername();

            if(nextUsername != null && !currentUsername.equals(nextUsername))
            {
                user.setUsername(nextUsername);
            }

            // Put Profile Url
            String currentProfileUrl = user.getProfile();
            String nextProfileUrl = null;
            String profileEditType = requestPutUserInfo.getProfile_edit_type();

            // Base64 -> MultipartFile
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(requestPutUserInfo.getProfile_image_base64().getBytes());
            Base64ToMultipartFile multipartFile = new Base64ToMultipartFile(bytes);

            boolean isExist;
            if(profileEditType.equals("delete")) {
                isExist = s3Uploader.existImage(currentProfileUrl);
                if(isExist) {
                    s3Uploader.deleteImage(currentProfileUrl);
                    nextProfileUrl = "";
                }
            }
            else if(profileEditType.equals("edit")) {
                if(currentProfileUrl == null)
                    isExist = false;
                else
                    isExist = s3Uploader.existImage(currentProfileUrl);
                if(isExist) {
                    try {
                        s3Uploader.deleteImage(currentProfileUrl);

                        String url = s3Uploader.uploadImage(multipartFile);
                        if(!url.isEmpty()) {
                            nextProfileUrl = url;
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        String url = s3Uploader.uploadImage(multipartFile);
                        if(!url.isEmpty()) {
                            nextProfileUrl = url;
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                nextProfileUrl = currentProfileUrl;
            }

            user.setProfile(nextProfileUrl);
            userRepository.save(user);

            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
