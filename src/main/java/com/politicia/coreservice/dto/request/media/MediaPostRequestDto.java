package com.politicia.coreservice.dto.request.media;

import com.politicia.coreservice.domain.MediaType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MediaPostRequestDto {
    @NotEmpty
    private Long postId;
    @NotNull
    private MediaType mediaType;
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String src;

}
