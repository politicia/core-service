package com.politicia.coreservice.domain.record.baseball;

import com.politicia.coreservice.domain.record.PlayerRecord;
import com.politicia.coreservice.domain.target.Player;
import com.politicia.coreservice.domain.target.Season;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class BaseballPitcherRecord extends PlayerRecord {

    private float era;
    private int wins;
    private int loses;
    private float innings;
    private int strikeOuts;
    private int hits;
    private int homeRuns;
    private int pointsAllowed;
    private int walks;
    private int dead;

    @Builder
    public BaseballPitcherRecord(Player player, Season season, float era, int wins, int loses, float innings, int strikeOuts, int hits, int homeRuns, int pointsAllowed, int walks, int dead) {
        setPlayer(player);
        setSeason(season);
        this.era = era;
        this.wins = wins;
        this.loses = loses;
        this.innings = innings;
        this.strikeOuts = strikeOuts;
        this.hits = hits;
        this.homeRuns = homeRuns;
        this.pointsAllowed = pointsAllowed;
        this.walks = walks;
        this.dead = dead;
    }
}
