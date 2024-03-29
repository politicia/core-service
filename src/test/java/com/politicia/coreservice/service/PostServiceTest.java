package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.domain.target.Team;
import com.politicia.coreservice.dto.request.post.PostPatchRequestDto;
import com.politicia.coreservice.dto.request.post.PostPostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.repository.PostRepository;
import com.politicia.coreservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void testCreatePost() {
        //given
        User user = User.builder()
                .id(1L)
                .name("userA")
                .build();
        Team team = Team.builder()
                .id(1L)
                .icon("icon")
                .name("name")
                .build();
        Post expectedPost = Post.builder()
                .id(1L)
                .user(user)
                .target(team)
                .title("title")
                .text("text")
                .build();
        PostPostRequestDto postPostRequestDto = PostPostRequestDto.builder()
                .userId(user.getId())
                .title("title")
                .text("text")
                .build();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(expectedPost);
        postService.createPost(postPostRequestDto);

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
        PostPatchRequestDto postPatchRequestDto = PostPatchRequestDto.builder()
                .title("new title")
                .build();
        //when
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedPost));
        postService.editPost(1L, postPatchRequestDto);

        //then
        assertEquals(expectedPost.getTitle(), postPatchRequestDto.getTitle());
    }

    @Test
    void testDeletePost() {
        //given
        Long postId = 1L;
        Post post = Post.builder().build();
        //when
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        postService.deletePost(1L);
        //then
        verify(postRepository, times(1)).delete(any(Post.class));

    }

    @Test
    void testGetPostById() {
        //given
        User user = User.builder()
                .id(1L)
                .name("userA")
                .build();
        Team team = Team.builder()
                .id(1L)
                .icon("icon")
                .name("name")
                .build();
        Post expectedPost = Post.builder()
                .id(1L)
                .user(user)
                .target(team)
                .title("title")
                .build();
        //when
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedPost));
        PostResponseDto post = postService.getPostById(1L);

        //then
        assertEquals(post.getTitle(), expectedPost.getTitle());
        assertEquals(post.getPostId(), expectedPost.getId());
        assertEquals(post.getTarget().getTargetId(), expectedPost.getTarget().toTargetDto().getTargetId());
        assertEquals(post.getTarget().getName(), expectedPost.getTarget().toTargetDto().getName());
        assertEquals(post.getTarget().getIcon(), expectedPost.getTarget().toTargetDto().getIcon());
    }

    @Test
    void testGetPostsByDate() {
        //given

        User user = User.builder()
                .build();
        Team team = Team.builder()
                .id(1L)
                .icon("icon")
                .name("name")
                .build();
        Post postA = Post.builder()
                .id(1L)
                .user(user)
                .target(team)
                .title("titleA")
                .build();
        Post postB = Post.builder()
                .id(2L)
                .user(user)
                .target(team)
                .title("titleB")
                .build();

        List postList = new ArrayList<Post>(List.of(postA, postB));
        Page<Post> posts = new PageImpl<>(postList);
        LocalDate today = LocalDate.now();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        when(postRepository.findByCreatedAtAfterAndCreatedAtBefore(
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 23, 59, 59),
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 00, 00, 00),
                pageRequest
                )).thenReturn(posts);
        Page<PostResponseDto> postsByDate = postService.getPostsByDate(today, 0);
        //then
        assertEquals(postsByDate.getContent().get(0).getPostId(), postA.getId());
        assertEquals(postsByDate.getContent().get(0).getTitle(), postA.getTitle());
        assertEquals(postsByDate.getContent().get(1).getPostId(), postB.getId());
        assertEquals(postsByDate.getContent().get(1).getTitle(), postB.getTitle());
    }

    @Test
    void testGetPostsByUser() {
        User user = User.builder()
                .id(1L)
                .build();
        Team team = Team.builder()
                .id(1L)
                .icon("icon")
                .name("name")
                .build();
        Post postA = Post.builder()
                .id(1L)
                .user(user)
                .target(team)
                .title("titleA")
                .build();
        Post postB = Post.builder()
                .id(2L)
                .user(user)
                .target(team)
                .title("titleB")
                .build();
        List postList = new ArrayList<Post>(List.of(postA, postB));
        Page<Post> posts = new PageImpl<>(postList);
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(postRepository.findByUser(any(User.class), any(Pageable.class))).thenReturn(posts);
        Page<PostResponseDto> postsByUser = postService.getPostsByUser(user.getId(), 0);

        //then
        assertEquals(postsByUser.getContent().get(0).getTitle(), postA.getTitle());
        assertEquals(postsByUser.getContent().get(1).getTitle(), postB.getTitle());

    }

//    @Test
//    void testGetPostsByMostViews() {
//        User user = User.builder()
//                .build();
//        User likeUser = User.builder().build();
//        Post postA = Post.builder()
//                .id(1L)
//                .user(user)
//                .title("titleA")
//                .build();
//        Post postB = Post.builder()
//                .id(2L)
//                .user(user)
//                .title("titleB")
//                .build();
//        PostLike postLike = PostLike.builder()
//                .id(1L)
//                .user(likeUser)
//                .post(postB)
//                .build();
//        List postList = new ArrayList<Post>(List.of(postA, postB));
//        Page<Post> posts = new PageImpl<>(postList);
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        //when
//        when(postRepository.findAll(Sort.)
//
//    }
//
//    @Test
//    void testGetPostsByMostLikes() {
//    }
//
//    @Test
//    void testGetHottestPosts() {
//    }

//    @Test
//    void testGetPostsByTarget() {
//        Team team = Team.builder()
//                .id(1L)
//                .name("team")
//                .icon("icon")
//                .build();
//        Post postA = Post.builder()
//                .id(1L)
//                .target(team)
//                .build();
//        Post postB = Post.builder()
//                .id(2L)
//                .target(team)
//                .build();
//
//        List postList = new ArrayList<Post>(List.of(postA, postB));
//        Page<Post> posts = new PageImpl<>(postList);
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        //when
//        when(postRepository.findByTarget(team)).thenReturn(posts);
//        Page<PostResponseDto> postsByTarget = postService.getPostsByTarget(team, 0);
//        //then
//        Assertions.assertEquals(postsByTarget.getContent().get(0).getPostId(), postA.getId());
//        Assertions.assertEquals(postsByTarget.getContent().get(1).getPostId(), postA.getId());
//    }
}