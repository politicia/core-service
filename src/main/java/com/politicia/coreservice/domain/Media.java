package com.politicia.coreservice.domain;

import com.politicia.coreservice.dto.response.MediaResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Media extends EntityPrefix {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    private String src;

    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Media(Long id, MediaType mediaType, String src, String thumbnail, Post post) {
        this.id = id;
        this.mediaType = mediaType;
        this.src = src;
        this.thumbnail = thumbnail;
        this.post = post;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public MediaResponseDto toDto() {
        return MediaResponseDto.builder()
                .mediaId(id)
                .postId(post.getId())
                .mediaType(mediaType)
                .src(src)
                .thumbnail(thumbnail)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
