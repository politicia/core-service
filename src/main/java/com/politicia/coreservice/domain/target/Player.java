package com.politicia.coreservice.domain.target;

import com.politicia.coreservice.domain.Target;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends Target {
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Player(Long id, String name, String icon, int age) {
        setId(id);
        setName(name);
        setIcon(icon);
        this.age = age;
    }
}
