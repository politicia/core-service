package com.politicia.coreservice.dto.request.post;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter @Builder
public class PostPostRequestDto {

    @NotNull
    private Long userId;
    @NotBlank
    private String title;
    @NotBlank
    @Size(max = 1000)
    private String text;
    @Nullable
    private Long targetId;

    @ConstructorProperties({"userId", "title", "text", "targetId"})
    public PostPostRequestDto(Long userId, String title, String text, Long targetId) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.targetId = targetId;
    }
}
