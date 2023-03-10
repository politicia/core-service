package com.politicia.coreservice.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter @Setter
@Builder
public class CommentPatchRequestDto {
    @NotBlank
    private String text;

    @ConstructorProperties({"text"})
    public CommentPatchRequestDto(String text) {
        this.text = text;
    }
}
