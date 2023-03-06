package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.PostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDto postRequestDto) {
        postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> editPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        return null;
    }
    @GetMapping("/list")
    public ResponseEntity<Page<PostResponseDto>> getPostList(@RequestParam Optional<Long> userId, @RequestParam Optional<LocalDate> date, @RequestParam int page) {
        if (userId.isPresent()) {

        }
        if (date.isPresent()) {

        }
        return null;
    }
}
