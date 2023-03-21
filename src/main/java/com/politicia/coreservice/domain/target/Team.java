package com.politicia.coreservice.domain.target;

import com.politicia.coreservice.domain.Target;
import com.politicia.coreservice.dto.response.target.TeamResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends Target {

    @OneToMany(mappedBy = "team")
    private List<Player> playerList;

    @Builder
    public Team(Long id, String name, String icon) {
        setId(id);
        setName(name);
        setIcon(icon);
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public TeamResponseDto toDto() {
        return TeamResponseDto.builder()
                .teamId(getId())
                .name(getName())
                .icon(getIcon())
                .players(playerList.stream()
                        .map(Player::toDto)
                        .toList())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
