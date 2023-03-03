package com.politicia.coreservice.dto.response;

import com.politicia.coreservice.domain.MediaType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class MediaResponseDto {
    private Long mediaId;
    private Long postId;
    private MediaType mediaType;
    private String src;
    private String thumbnail;
}
