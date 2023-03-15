package com.politicia.coreservice.dto.request.media;

import com.politicia.coreservice.domain.MediaType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Getter @Setter @Builder
public class MediaPostRequestDto {
    @NotNull
    private Long postId;
    @NotNull
    private MediaType mediaType;

    @Nullable
    private MultipartFile file;

    @ConstructorProperties({"postId", "mediaType", "file"})
    public MediaPostRequestDto(Long postId, MediaType mediaType, MultipartFile file) {
        this.postId = postId;
        this.mediaType = mediaType;
        this.file = file;
    }
}
