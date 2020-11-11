package com.taekyeong.tkgram.service.post;

import com.taekyeong.tkgram.dto.PhotoDto;
import com.taekyeong.tkgram.entity.Photo;
import com.taekyeong.tkgram.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    @Transactional
    public Photo postPhoto(PhotoDto photoDto) {
        return photoRepository.save(photoDto.toEntity());
    }
}
