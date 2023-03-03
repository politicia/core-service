package com.politicia.coreservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends EntityPrefix {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private String text;

    @Builder
    public Comment(Long id, User user, Post post, String text) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.text = text;
    }
}
