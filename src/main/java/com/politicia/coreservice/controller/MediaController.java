package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.MediaRequestDto;
import com.politicia.coreservice.dto.response.MediaResponseDto;
import com.politicia.coreservice.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @PostMapping
    public ResponseEntity<Void> createMedia(@RequestBody MediaRequestDto mediaRequestDto) {
        return null;
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long mediaId) {
        return null;
    }
    @GetMapping("/{postId}")
    public ResponseEntity<MediaResponseDto> getMedia(@PathVariable Long postId, int page) {
        return null;
    }

}
