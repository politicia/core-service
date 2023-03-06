package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.CommentRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(CommentRequestDto commentRequestDto) {
        return null;
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> editComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return null;
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        return null;
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getComments(Long userId, Long postId, int page) {
        return null;
    }
}

