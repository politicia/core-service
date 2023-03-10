package com.politicia.coreservice.dto.response;

import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder
public class CommentResponseDto {
    private Long commentId;
    private UserResponseDto user;
    private Long postId;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
