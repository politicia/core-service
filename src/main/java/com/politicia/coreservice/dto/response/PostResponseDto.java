package com.politicia.coreservice.dto.response;

import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
public class PostResponseDto {

    private Long postId;
    private UserResponseDto user;
    private String title;
    private String text;
    private TargetResponseDto target;
    private List<MediaResponseDto> mediaList;
    private List<LikeResponseDto> likes;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
