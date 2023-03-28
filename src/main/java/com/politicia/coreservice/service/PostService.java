package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.post.PostPatchRequestDto;
import com.politicia.coreservice.dto.request.post.PostPostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.repository.PostRepository;
import com.politicia.coreservice.repository.UserRepository;
import com.politicia.coreservice.repository.like.PostLikeRepository;
import com.politicia.coreservice.repository.target.TargetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TargetRepository targetRepository;
    private final PostLikeRepository postLikeRepository;

    public void createPost(PostPostRequestDto postPostRequestDto) {
        User user = userRepository.findById(postPostRequestDto.getUserId()).orElseThrow(() -> new NoSuchElementException(String.format("No such user with ID %s", postPostRequestDto.getUserId())));

        Post newPost = Post.builder()
                        .user(user)
                        .title(postPostRequestDto.getTitle())
                        .text(postPostRequestDto.getText())
                        .build();
        if (postPostRequestDto.getTargetId() != null) {
            Target target = targetRepository.findById(postPostRequestDto.getTargetId()).orElseThrow(() -> new NoSuchElementException(String.format("No such target with ID %s", postPostRequestDto.getTargetId())));
            newPost.setTarget(target);
        }
        postRepository.save(newPost);
    }

    public void editPost(Long postId, PostPatchRequestDto postPatchRequestDto) throws NoSuchElementException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(String.format("No such post with ID %s", postId)));

        if (postPatchRequestDto.getTitle() != null) {
            post.setTitle(postPatchRequestDto.getTitle());
        }
        if (postPatchRequestDto.getText() != null) {
            post.setText(postPatchRequestDto.getText());
        }
        if (postPatchRequestDto.getTargetId() != null) {
            Target target = targetRepository.findById(postPatchRequestDto.getTargetId()).orElseThrow(() -> new NoSuchElementException(String.format("No such target with ID %s", postPatchRequestDto.getTargetId())));
            post.setTarget(target);
        }
        post.setUpdatedAt(LocalDateTime.now());
    }

    public void deletePost(Long postId) throws NoSuchElementException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(String.format("No such post with ID %s", postId)));
        postRepository.delete(post);
    }

    public PostResponseDto getPostById(Long postId) throws NoSuchElementException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(String.format("No such post with ID %s", postId)));
        return post.toDto();
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

//    public Page<PostResponseDto> getPostsByMostViews() {
//        return null;
//    }
//
//    public Page<PostResponseDto> getPostsByMostLikes() {
//        return null;
//    }
//
//    public Page<PostResponseDto> getHottestPosts(LocalDateTime localDateTime) {
//        return null;
//    }

//    public Page<PostResponseDto> getPostsByTarget(Long targetId, int page) {
//
//        PageRequest pageRequest = PageRequest.of(page, 10);
//
//    }
}
