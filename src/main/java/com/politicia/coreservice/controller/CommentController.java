package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.comment.CommentPatchRequestDto;
import com.politicia.coreservice.dto.request.comment.CommentPostRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;    

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody @Validated CommentPostRequestDto commentPostRequestDto) {
        commentService.createComment(commentPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> editComment(@PathVariable Long commentId, @RequestBody @Validated CommentPatchRequestDto commentPatchRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        commentService.editComment(commentId, commentPatchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByUserId(@PathVariable Long userId, @RequestParam int page) {
        return ResponseEntity.ok().body(commentService.getCommentListByUser(userId, page));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId, @RequestParam int page) {
        return ResponseEntity.ok().body(commentService.getCommentListByPost(postId, page));
    }
}

