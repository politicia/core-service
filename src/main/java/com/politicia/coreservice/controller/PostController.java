package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.post.PostPatchRequestDto;
import com.politicia.coreservice.dto.request.post.PostPostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost( PostPostRequestDto postPostRequestDto) {
        postService.createPost(postPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> editPost(@PathVariable Long postId, PostPatchRequestDto postPatchRequestDto) {
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
    public ResponseEntity<Page<PostResponseDto>> getPostList(@RequestParam Optional<Long> userId, @RequestParam Optional<LocalDate> date, @RequestParam int page) {
        if (userId.isPresent()) {
            return ResponseEntity.ok().body(postService.getPostsByUser(userId.get(), page));
        }
        else if (date.isPresent()) {
            return ResponseEntity.ok().body(postService.getPostsByDate(date.get(), page));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
