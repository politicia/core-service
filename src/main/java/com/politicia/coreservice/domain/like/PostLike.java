package com.politicia.coreservice.domain.like;

import com.politicia.coreservice.domain.Like;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends Like {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostLike(Long id, User user, Post post) {
        setId(id);
        setUser(user);
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
        this.post = post;
    }
}
