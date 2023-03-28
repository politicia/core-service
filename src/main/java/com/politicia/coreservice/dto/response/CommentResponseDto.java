package com.politicia.coreservice.dto.response;

import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
public class CommentResponseDto {
    private Long commentId;
    private UserResponseDto user;
    private Long postId;
    private String text;
    private List<LikeResponseDto> likes;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
