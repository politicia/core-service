package com.politicia.coreservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User extends EntityPrefix{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nationality;
    private String profilePic;

    @Builder
    public User(Long id, String username, String nationality) {
        this.id = id;
        this.username = username;
        this.nationality = nationality;
    }
}
