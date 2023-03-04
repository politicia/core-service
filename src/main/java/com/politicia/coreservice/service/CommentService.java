package com.politicia.coreservice.service;

import com.politicia.coreservice.dto.request.CommentRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void createComment(CommentRequestDto commentRequestDto) {}

    public CommentResponseDto editComment(Long commentId, CommentRequestDto commentRequestDto) {
        return null;
    }

    public void deleteComment(Long commentId) {
    }

    public Page<CommentResponseDto> getCommentListByPost(Long postId) {
        return null;
    }

    public Page<CommentResponseDto> getCommentListByUser(Long userId) {
        return null;
    }
}

//## Comments
//
//        - Create Comment
//        - Edit Comment
//        - Delete Comment
//        - Get Comment List
//        - Post
//        - User
