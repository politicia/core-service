package com.politicia.coreservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class UserResponseDto {

    private Long id;
    private String name;
    private String nationality;
    private String profilePic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
