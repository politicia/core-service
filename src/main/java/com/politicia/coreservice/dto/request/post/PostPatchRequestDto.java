package com.politicia.coreservice.dto.request.post;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter @Builder
public class PostPatchRequestDto {
    @Nullable
    private String title;
    @Nullable
    private String text;
    @Nullable
    private Long targetId;
    @ConstructorProperties({"title", "text", "targetId"})
    public PostPatchRequestDto(String title, String text, Long targetId) {
        this.title = title;
        this.text = text;
        this.targetId = targetId;
    }
}
