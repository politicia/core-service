package com.politicia.coreservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class LikeResponseDto {

    private Long likeId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
