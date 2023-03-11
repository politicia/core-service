package com.politicia.coreservice.domain;

import com.politicia.coreservice.domain.like.PostLike;
import com.politicia.coreservice.dto.response.PostResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes;

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
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public PostResponseDto toDto() {
        return PostResponseDto.builder()
                .postId(id)
                .user(user.toDto())
                .title(title)
                .text(text)
                .target(target)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();

    }
}
