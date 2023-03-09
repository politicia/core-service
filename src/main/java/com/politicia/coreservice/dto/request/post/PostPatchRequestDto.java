package com.politicia.coreservice.dto.request.post;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PostPatchRequestDto {
    @Nullable
    private String title;
    @Nullable
    private String text;
    @Nullable
    private Long targetId;
}
