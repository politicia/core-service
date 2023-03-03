package com.politicia.coreservice.domain.target;

import com.politicia.coreservice.domain.Target;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

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
    }
}
