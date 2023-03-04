package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.PostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.repository.PostRepository;
import com.politicia.coreservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createPost(PostRequestDto postRequestDto) {
        Post newPost = postRequestDto.toEntity();
        postRepository.save(newPost);
    }

    public void editPost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).get();
        post.setTitle(postRequestDto.getTitle());
        post.setTarget(postRequestDto.getTarget());
        post.setText(postRequestDto.getText());
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).get();
        return PostResponseDto.builder()
                .postId(post.getId())
                .user(post.getUser())
                .title(post.getTitle())
                .text(post.getText())
                .build();
    }

    public Page<PostResponseDto> getPostsByDate(LocalDate localDate, int page) {
        LocalDateTime startTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 23, 59, 59);
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> posts = postRepository.findByCreatedAtAfterAndCreatedAtBefore(endTime, startTime, pageRequest);
        return posts.map(Post::toDto);
    }

    public Page<PostResponseDto> getPostsByUser(Long userId, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        User user = userRepository.findById(userId).get();
        Page<Post> posts = postRepository.findByUser(user, pageRequest);
        return posts.map(Post::toDto);
    }

    public Page<PostResponseDto> getPostsByMostViews() {
        return null;
    }

    public Page<PostResponseDto> getPostsByMostLikes() {
        return null;
    }

    public Page<PostResponseDto> getHottestPosts(LocalDateTime localDateTime) {
        return null;
    }

//    public Page<PostResponseDto> getPostsByTarget(Long targetId, int page) {
//
//        PageRequest pageRequest = PageRequest.of(page, 10);
//
//    }
}
