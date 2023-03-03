package com.politicia.coreservice.domain.record;

import com.politicia.coreservice.domain.Record;
import com.politicia.coreservice.domain.target.Season;
import com.politicia.coreservice.domain.target.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TeamRecord extends Record {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;
    private int wins;
    private int draws;
    private int loses;
    private int streak;
}
