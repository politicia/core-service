package com.politicia.coreservice.domain.target;

import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.dto.response.TargetResponseDto;
import com.politicia.coreservice.dto.response.target.PlayerResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends Target {
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Player(Long id, String name, Team team, String icon, int age) {
        setId(id);
        setName(name);
        setIcon(icon);
        this.team = team;
        this.age = age;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public PlayerResponseDto toDto() {
        return PlayerResponseDto.builder()
                .playerId(getId())
                .name(getName())
                .icon(getIcon())
                .age(age)
                .team(team.toDto())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
