package com.politicia.coreservice.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommentPostRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
    @NotBlank
    private String text;
}
