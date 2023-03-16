package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.comment.CommentPatchRequestDto;
import com.politicia.coreservice.dto.request.comment.CommentPostRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.repository.CommentRepository;
import com.politicia.coreservice.repository.PostRepository;
import com.politicia.coreservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createComment(CommentPostRequestDto commentRequestDto) {
        User user = userRepository.findById(commentRequestDto.getUserId()).get();
        Post post = postRepository.findById(commentRequestDto.getPostId()).get();
        Comment newComment = Comment.builder()
                        .user(user)
                        .post(post)
                        .text(commentRequestDto.getText())
                        .build();
        commentRepository.save(newComment);
    }

    public void editComment(Long commentId, CommentPatchRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.setText(commentRequestDto.getText());
        comment.setUpdatedAt(LocalDateTime.now());
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        commentRepository.delete(comment);
    }

    public Page<CommentResponseDto> getCommentListByPost(Long postId, int page) {
        PageRequest pr = PageRequest.of(page, 20);
        Post post = postRepository.findById(postId).get();
        Page<Comment> comments = commentRepository.findByPost(post, pr);
        return comments.map(Comment::toDto);
    }

    public Page<CommentResponseDto> getCommentListByUser(Long userId, int page) {
        PageRequest pr = PageRequest.of(page, 20);
        User user = userRepository.findById(userId).get();
        Page<Comment> comments = commentRepository.findByUser(user, pr);
        return comments.map(Comment::toDto);
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
