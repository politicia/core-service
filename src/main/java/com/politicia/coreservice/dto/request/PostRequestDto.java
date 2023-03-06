package com.politicia.coreservice.dto.request;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PostRequestDto {
    private Long userId;
    private String title;
    private String text;
    private Long targetId;


    public Post toEntity() {
        return Post.builder()
                .title(title)
                .text(text)
                .build();
    }
}
