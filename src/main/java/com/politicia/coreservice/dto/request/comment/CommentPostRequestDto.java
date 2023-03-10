package com.politicia.coreservice.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter @Builder
public class CommentPostRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
    @NotBlank
    private String text;

    @ConstructorProperties({"userId", "postId", "text"})
    public CommentPostRequestDto(Long userId, Long postId, String text) {
        this.userId = userId;
        this.postId = postId;
        this.text = text;
    }
}
