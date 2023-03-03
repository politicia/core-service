package com.politicia.coreservice.dto.response;

import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CommentResponseDto {
    private Long commentId;
    private User user;
    private Long postId;
    private String text;
}
