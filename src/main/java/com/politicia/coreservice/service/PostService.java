package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.PostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PostService {

    void createPost(PostRequestDto postRequestDto);
    void editPost(PostRequestDto postRequestDto);
    void deletePost(PostRequestDto postRequestDto);
    PostResponseDto getPost(Long id);
    Page<PostResponseDto> getPostsByDate(LocalDate localDate);
    Page<PostResponseDto> getPostsByUser(User user);
    Page<PostResponseDto> getPostsByMostViews();
    Page<PostResponseDto> getPostsByMostLikes();
    Page<PostResponseDto> getHottestPosts(LocalDateTime localDateTime);
    Page<PostResponseDto> getPostsByTarget(Target target);
}

/**
## Post

        - Create Post
        - Edit Post
        - Delete Post
        - Get Post Details
        - Get Post list
        - Date
        - User
        - Most Views
        - Most Likes
        - Hot
        - Target

**/