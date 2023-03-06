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
    private User user;
    private String title;
    private String text;
    private Target target;
    private List<String> mediaList;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
