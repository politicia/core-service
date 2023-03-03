package com.politicia.coreservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends EntityPrefix {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private Target target;

    @OneToMany(mappedBy = "post")
    private List<Media> mediaList;

    @Builder
    public Post(Long id, User user, String title, String text, Target target) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.text = text;
        this.target = target;
    }
}
