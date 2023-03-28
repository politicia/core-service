package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.post.PostPatchRequestDto;
import com.politicia.coreservice.dto.request.post.PostPostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Validated PostPostRequestDto postPostRequestDto) {
        postService.createPost(postPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> editPost(@PathVariable Long postId, @RequestBody @Validated PostPatchRequestDto postPatchRequestDto) {
        postService.editPost(postId, postPatchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPostById(postId);
        return ResponseEntity.ok().body(postResponseDto);
    }
    @GetMapping("/list")
    public ResponseEntity<Page<PostResponseDto>> getPostList(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate date, @RequestParam(required = false) Long userId, @RequestParam int page) {
        if (userId != null) {
            return ResponseEntity.ok().body(postService.getPostsByUser(userId, page));
        }
        else if (date != null) {
            return ResponseEntity.ok().body(postService.getPostsByDate(date, page));
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.likePost(postId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.unlikePost(postId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
