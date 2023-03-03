package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.PostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.repository.MediaRepository;
import com.politicia.coreservice.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MediaRepository mediaRepository;

    @Test
    void testCreatePost() {
        //given
        User user = User.builder()
                .id(1L)
                .name("userA")
                .build();
        Post expectedPost = Post.builder()
                .id(1L)
                .user(user)
                .title("title")
                .build();
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .user(user)
                .title("title")
                .build();
        //when
        when(postRepository.save(any(Post.class))).thenReturn(expectedPost);
        postService.createPost(postRequestDto);

        //then
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testEditPost() {
        //given
        User user = User.builder()
                .id(1L)
                .name("userA")
                .build();
        Post expectedPost = Post.builder()
                .id(1L)
                .user(user)
                .title("title")
                .build();
        PostRequestDto editRequest = PostRequestDto.builder()
                .id(1L)
                .title("new title")
                .build();
        //when
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedPost));
        postService.editPost(editRequest);
        PostResponseDto post = postService.getPost(1L);

        //then
        Assertions.assertEquals(post.getTitle(), editRequest.getTitle());
    }

    @Test
    void testDeletePost() {
        //given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .id(1L)
                .build();
        //when
        postService.deletePost(postRequestDto);
        //then
        verify(postRepository, times(1)).delete(any(Post.class));

    }

    @Test
    void testGetPost() {
        //given
        User user = User.builder()
                .id(1L)
                .name("userA")
                .build();
        Post expectedPost = Post.builder()
                .id(1L)
                .user(user)
                .title("title")
                .build();
        //when
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedPost));
        PostResponseDto post = postService.getPost(1L);

        //then
        Assertions.assertEquals(post.getTitle(), expectedPost.getTitle());
    }

    @Test
    void testGetPostsByDate() {
        //given

        User user = User.builder()
                .build();
        Post postA = Post.builder()
                .id(1L)
                .user(user)
                .title("titleA")
                .build();
        Post postB = Post.builder()
                .id(2L)
                .user(user)
                .title("titleB")
                .build();

        List postList = new ArrayList<Post>(List.of(postA, postB));
        Page<Post> posts = new PageImpl<>(postList);
        LocalDate today = LocalDate.now();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        when(postRepository.findByCreatedAtAfterAndCreatedAtBefore(
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 00, 00, 00),
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 23, 59, 59),
                pageRequest
                )).thenReturn(posts);
        Page<PostResponseDto> postsByDate = postService.getPostsByDate(today);
        //then
        verify(postRepository.findByCreatedAtAfterAndCreatedAtBefore(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)), times(1));
        Assertions.assertEquals(postsByDate.getContent().get(0).getPostId(), postA.getId());
        Assertions.assertEquals(postsByDate.getContent().get(0).getTitle(), postA.getTitle());
        Assertions.assertEquals(postsByDate.getContent().get(1).getPostId(), postB.getId());
        Assertions.assertEquals(postsByDate.getContent().get(1).getTitle(), postB.getTitle());
    }

    @Test
    void testGetPostsByUser() {
    }

    @Test
    void testGetPostsByMostViews() {
    }

    @Test
    void testGetPostsByMostLikes() {
    }

    @Test
    void testGetHottestPosts() {
    }

    @Test
    void testGetPostsByTarget() {
    }
}