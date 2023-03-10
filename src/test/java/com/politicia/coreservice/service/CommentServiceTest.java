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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void createComment() {
        //given
        User user = User.builder()
                .id(1L)
                .build();
        Post post = Post.builder()
                .id(1L)
                .build();
        CommentPostRequestDto commentPostRequestDto = CommentPostRequestDto.builder()
                .userId(1L)
                .postId(1L)
                .text("text")
                .build();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        commentService.createComment(commentPostRequestDto);
        //then
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findById(1L);
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
        CommentPatchRequestDto commentPatchRequestDto = CommentPatchRequestDto.builder()
                .text("newText")
                .build();
        //when
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));
        commentService.editComment(commentId, commentPatchRequestDto);
        //then
        Assertions.assertEquals(comment.getText(), "newText");
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
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        commentService.deleteComment(commentId);

        //then
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void getCommentListByPost() {
        //given
        User user = User.builder()
                .id(1L)
                .build();
        Post post = Post.builder()
                .id(1L)
                .user(user)
                .build();
        Comment commentA = Comment.builder()
                .id(1L)
                .post(post)
                .user(user)
                .text("textA")
                .build();
        Comment commentB = Comment.builder()
                .id(2L)
                .post(post)
                .user(user)
                .text("textB")
                .build();
        Page<Comment> expectedComments = new PageImpl<>(new ArrayList<>(List.of(commentA, commentB)));

        //when
        when(commentRepository.findByPost(any(Post.class), any(Pageable.class))).thenReturn(expectedComments);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Page<CommentResponseDto> commentListByPost = commentService.getCommentListByPost(post.getId(), 0);
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
        Post post = Post.builder()
                .id(1L)
                .build();
        Comment commentA = Comment.builder()
                .id(1L)
                .user(user)
                .post(post)
                .text("textA")
                .build();
        Comment commentB = Comment.builder()
                .id(2L)
                .user(user)
                .post(post)
                .text("textB")
                .build();
        Page<Comment> expectedComments = new PageImpl<>(new ArrayList<>(List.of(commentA, commentB)));

        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.findByUser(any(User.class), any(Pageable.class))).thenReturn(expectedComments);
        Page<CommentResponseDto> commentListByUser = commentService.getCommentListByUser(user.getId(), 0);
        //then
        assertEquals(commentListByUser.getContent().get(0).getText(), commentA.getText());
        assertEquals(commentListByUser.getContent().get(1).getText(), commentB.getText());
    }
}