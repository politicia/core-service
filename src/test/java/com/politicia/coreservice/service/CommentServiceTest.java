package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.CommentRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;

    @Test
    void createComment() {
        //given
//        Comment comment = Comment.builder()
//                .id(1L)
//                .text("text")
//                .build();
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .text("text")
                .build();
        //when
//        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        commentService.createComment(commentRequestDto);
        //then
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void editComment() {
        //given
        Comment comment = Comment.builder()
                .id(1L)
                .text("text")
                .build();
        Long commentId = 1L;
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .text("newText")
                .build();
        //when
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));
        CommentResponseDto commentResponseDto = commentService.editComment(commentId, commentRequestDto);
        //then
        Assertions.assertEquals(commentResponseDto.getText(), "newText");
    }

    @Test
    void deleteComment() {
        //given
        Comment comment = Comment.builder()
                .id(1L)
                .text("text")
                .build();
        Long commentId = 1L;
        //when
        commentService.deleteComment(commentId);

        //then
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void getCommentListByPost() {
        //given
        Post post = Post.builder()
                .id(1L)
                .build();
        Comment commentA = Comment.builder()
                .id(1L)
                .post(post)
                .text("textA")
                .build();
        Comment commentB = Comment.builder()
                .id(2L)
                .post(post)
                .text("textB")
                .build();
        Page<Comment> expectedComments = new PageImpl<>(new ArrayList<>(List.of(commentA, commentB)));

        //when
        when(commentRepository.findByPost(any(Post.class))).thenReturn(expectedComments);
        Page<CommentResponseDto> commentListByPost = commentService.getCommentListByPost(post.getId());
        //then
        assertEquals(commentListByPost.getContent().get(0).getText(), commentA.getText());
        assertEquals(commentListByPost.getContent().get(1).getText(), commentB.getText());
    }

    @Test
    void getCommentListByUser() {
        //given
        User user = User.builder()
                .id(1L)
                .name("test")
                .build();
        Comment commentA = Comment.builder()
                .id(1L)
                .user(user)
                .text("textA")
                .build();
        Comment commentB = Comment.builder()
                .id(2L)
                .user(user)
                .text("textB")
                .build();
        Page<Comment> expectedComments = new PageImpl<>(new ArrayList<>(List.of(commentA, commentB)));

        //when
        when(commentRepository.findByUser(any(User.class))).thenReturn(expectedComments);
        Page<CommentResponseDto> commentListByUser = commentService.getCommentListByUser(user.getId());
        //then
        assertEquals(commentListByUser.getContent().get(0).getText(), commentA.getText());
        assertEquals(commentListByUser.getContent().get(1).getText(), commentB.getText());
    }
}