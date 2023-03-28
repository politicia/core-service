package com.politicia.coreservice.domain.like;

import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.EntityPrefix;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.response.LikeResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends EntityPrefix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @Builder
    public CommentLike(Long commentLikeId, User user, Comment comment) {
        this.commentLikeId = commentLikeId;
        this.user = user;
        this.comment = comment;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public LikeResponseDto toDto() {
        return LikeResponseDto.builder()
                .likeId(commentLikeId)
                .userId(user.getId())
                .build();
    }
}
