package com.politicia.coreservice.dto.request;

import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CommentRequestDto {
    private Long userId;
    private Long postId;
    private String text;

}
