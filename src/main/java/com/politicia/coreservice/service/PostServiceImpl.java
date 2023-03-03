package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.PostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostServiceImpl implements PostService {
    @Override
    public void createPost(PostRequestDto postRequestDto) {

    }

    @Override
    public void editPost(PostRequestDto postRequestDto) {

    }

    @Override
    public void deletePost(PostRequestDto postRequestDto) {

    }

    @Override
    public PostResponseDto getPost(Long id) {
        return null;
    }

    @Override
    public Page<PostResponseDto> getPostsByDate(LocalDate localDate) {
        return null;
    }

    @Override
    public Page<PostResponseDto> getPostsByUser(User user) {
        return null;
    }

    @Override
    public Page<PostResponseDto> getPostsByMostViews() {
        return null;
    }

    @Override
    public Page<PostResponseDto> getPostsByMostLikes() {
        return null;
    }

    @Override
    public Page<PostResponseDto> getHottestPosts(LocalDateTime localDateTime) {
        return null;
    }

    @Override
    public Page<PostResponseDto> getPostsByTarget(Target target) {
        return null;
    }
}
