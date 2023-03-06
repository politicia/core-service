package com.politicia.coreservice.domain;

import com.politicia.coreservice.dto.response.UserResponseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User extends EntityPrefix{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nationality;
    private String profilePic;

    @Builder
    public User(Long id, String name, String nationality, String profilePic) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.profilePic = profilePic;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public UserResponseDto toDto() {
        return UserResponseDto.builder()
                .id(id)
                .name(name)
                .nationality(nationality)
                .profilePic(profilePic)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
