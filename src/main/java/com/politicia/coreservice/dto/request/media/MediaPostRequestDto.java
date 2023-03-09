package com.politicia.coreservice.dto.request.media;

import com.politicia.coreservice.domain.MediaType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Builder
public class MediaPostRequestDto {
    @NotEmpty
    private Long postId;
    @NotNull
    private MediaType mediaType;
    @NotNull
    private MultipartFile file;

}
