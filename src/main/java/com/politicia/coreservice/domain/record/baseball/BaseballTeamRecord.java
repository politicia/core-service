package com.politicia.coreservice.domain.record.baseball;

import com.politicia.coreservice.domain.record.TeamRecord;
import com.politicia.coreservice.domain.target.Season;
import com.politicia.coreservice.domain.target.Team;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class BaseballTeamRecord extends TeamRecord {

    private int totalBoxes;
    private int totalHits;
    private float avg;
    private float obp;
    private float slg;
    private float era;
    private int lastWins;
    private int lastDraws;
    private int lastLoses;

    @Builder
    public BaseballTeamRecord(Team team, Season season, int wins, int draws, int loses, int streak, int totalBoxes, int totalHits, float avg, float obp, float slg, float era, int lastWins, int lastDraws, int lastLoses) {
        setTeam(team);
        setSeason(season);
        setWins(wins);
        setDraws(draws);
        setLoses(loses);
        setStreak(streak);
        this.totalBoxes = totalBoxes;
        this.totalHits = totalHits;
        this.avg = avg;
        this.obp = obp;
        this.slg = slg;
        this.era = era;
        this.lastWins = lastWins;
        this.lastDraws = lastDraws;
        this.lastLoses = lastLoses;
    }
}
