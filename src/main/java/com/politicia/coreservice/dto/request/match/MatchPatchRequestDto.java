package com.politicia.coreservice.dto.request.match;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder
public class MatchPatchRequestDto {
    private String matchStatus;
    private Long homeTeamId;
    private Long awayTeamId;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;
}
