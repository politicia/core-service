package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.media.MediaPostRequestDto;
import com.politicia.coreservice.dto.response.MediaResponseDto;
import com.politicia.coreservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @PostMapping
    public ResponseEntity<Void> createMedia(@RequestBody @Validated MediaPostRequestDto mediaPostRequestDto) {
        try {
            mediaService.createMedia(mediaPostRequestDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long mediaId) {
        mediaService.deleteMedia(mediaId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{postId}")
    public ResponseEntity<Page<MediaResponseDto>> getMedia(@PathVariable Long postId, @RequestParam int page) {
        Page<MediaResponseDto> mediaList = mediaService.getMediaListByPost(postId, page);
        return ResponseEntity.ok().body(mediaList);
    }

}
