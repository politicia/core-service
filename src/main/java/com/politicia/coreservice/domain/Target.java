package com.politicia.coreservice.domain;

import com.politicia.coreservice.dto.response.TargetResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Target extends EntityPrefix {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String icon;
    @OneToMany(mappedBy = "target")
    private List<Post> postList;

    public TargetResponseDto toTargetDto() {
        return TargetResponseDto.builder()
                .targetId(id)
                .name(name)
                .icon(icon)
                .build();
    }

}
