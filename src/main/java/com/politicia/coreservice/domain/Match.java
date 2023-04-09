package com.politicia.coreservice.domain;

import com.politicia.coreservice.domain.target.Season;
import com.politicia.coreservice.domain.target.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity @Getter
public class Match extends EntityPrefix {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    private String matchStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team awayTeam;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;

    protected Match() {
    }

    @Builder
    public Match(Long matchId, String matchStatus, Season season, Team homeTeam, Team awayTeam, int homeScore, int awayScore, LocalDateTime matchDate) {
        this.matchId = matchId;
        this.matchStatus = matchStatus;
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.matchDate = matchDate;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }
}
