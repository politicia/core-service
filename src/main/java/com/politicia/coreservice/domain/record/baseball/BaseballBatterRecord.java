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
public class BaseballBatterRecord extends PlayerRecord {

    private int totalBoxes;
    private int totalHits;
    private float avg;
    private float obp;
    private float slg;
    private int singles;
    private int doubles;
    private int triples;
    private int homeRuns;
    private int stolenBases;
    private int walks;
    private int strikeOuts;

    @Builder
    public BaseballBatterRecord(Player player, Season season, int totalBoxes, int totalHits, float avg, float obp, float slg, int singles, int doubles, int triples, int homeRuns, int stolenBases, int walks, int strikeOuts) {
        setPlayer(player);
        setSeason(season);
        this.totalBoxes = totalBoxes;
        this.totalHits = totalHits;
        this.avg = avg;
        this.obp = obp;
        this.slg = slg;
        this.singles = singles;
        this.doubles = doubles;
        this.triples = triples;
        this.homeRuns = homeRuns;
        this.stolenBases = stolenBases;
        this.walks = walks;
        this.strikeOuts = strikeOuts;
    }
}
