package com.politicia.coreservice.dto.request;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PostRequestDto {
    private Long id;
    private User user;
    private String title;
    private String text;
    private Target target;

    public void setUser(User user) {
        this.user = user;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Post toEntity() {
        return Post.builder()
                .user(user)
                .target(target)
                .title(title)
                .text(text)
                .build();
    }
}
