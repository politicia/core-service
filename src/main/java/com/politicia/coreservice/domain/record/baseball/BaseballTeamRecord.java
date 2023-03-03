package com.politicia.coreservice.domain.record.baseball;

import com.politicia.coreservice.domain.record.TeamRecord;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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
}
