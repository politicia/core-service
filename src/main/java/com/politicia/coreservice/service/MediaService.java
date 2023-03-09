package com.politicia.coreservice.service;

import com.politicia.coreservice.dto.request.media.MediaPostRequestDto;
import com.politicia.coreservice.dto.response.MediaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    public void createMedia(MediaPostRequestDto mediaPostRequestDto) {

    }
    public void deleteMedia(Long mediaId) {

    }
    public Page<MediaResponseDto> getMediaListByPost(Long postId) {
        return null;
    }
}
