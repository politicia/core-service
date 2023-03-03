package com.politicia.coreservice.domain.record;

import com.politicia.coreservice.domain.Record;
import com.politicia.coreservice.domain.target.Player;
import com.politicia.coreservice.domain.target.Season;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PlayerRecord extends Record {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;
}
