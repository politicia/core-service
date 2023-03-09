package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.comment.CommentPatchRequestDto;
import com.politicia.coreservice.dto.request.comment.CommentPostRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(CommentPostRequestDto commentPostRequestDto) {
        commentService.createComment(commentPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> editComment(@PathVariable Long commentId, @Valid @RequestBody CommentPatchRequestDto commentPatchRequestDto) {
        commentService.editComment(commentId, commentPatchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getComments(@RequestParam Long userId, Long postId, int page) {
        if (userId != null) {
            return ResponseEntity.ok().body(commentService.getCommentListByUser(userId, page));
        }
        if (postId != null) {
            return ResponseEntity.ok().body(commentService.getCommentListByPost(postId, page));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

