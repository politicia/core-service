package com.politicia.coreservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder
public class MatchResponseDto {

    private Long matchId;
    private String matchStatus;
    private TargetResponseDto season;
    private TargetResponseDto homeTeam;
    private TargetResponseDto awayTeam;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
