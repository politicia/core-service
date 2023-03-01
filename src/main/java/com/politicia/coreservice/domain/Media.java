package com.politicia.coreservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Media extends EntityPrefix {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MediaType mediaType;

    private String src;

    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
