package com.politicia.coreservice.dto.request.match;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder
public class MatchPostRequestDto {
    private Long seasonId;
    private Long homeTeamId;
    private Long awayTeamId;
    private LocalDateTime matchDate;
}
