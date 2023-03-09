package com.politicia.coreservice.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommentPatchRequestDto {
    @NotBlank
    private String text;
}
