package com.politicia.coreservice.domain;

import com.politicia.coreservice.domain.like.CommentLike;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends EntityPrefix {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private String text;
    @OneToMany(mappedBy = "comment")
    private List<CommentLike> likes;

    @Builder
    public Comment(Long id, User user, Post post, String text) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.text = text;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public CommentResponseDto toDto() {
        return CommentResponseDto.builder()
                .commentId(id)
                .user(user.toDto())
                .postId(post.getId())
                .text(text)
                .likes(likes.stream().map(CommentLike::toDto).toList())
                .likeCount(likes.size())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();

    }
}
