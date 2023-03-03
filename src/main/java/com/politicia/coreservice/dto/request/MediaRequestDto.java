package com.politicia.coreservice.dto.request;

import com.politicia.coreservice.domain.MediaType;
import com.politicia.coreservice.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @Builder
public class MediaRequestDto {
    private Post post;
    private MediaType mediaType;
    private MultipartFile content;
}
