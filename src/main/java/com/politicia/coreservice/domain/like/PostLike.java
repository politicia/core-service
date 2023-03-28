package com.politicia.coreservice.domain.like;

import com.politicia.coreservice.domain.EntityPrefix;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.response.LikeResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends EntityPrefix {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Builder
    public PostLike(Long postLikeId, User user, Post post) {
        this.postLikeId = postLikeId;
        this.user = user;
        this.post = post;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public LikeResponseDto toDto() {
        return LikeResponseDto.builder()
                .likeId(postLikeId)
                .userId(user.getId())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
