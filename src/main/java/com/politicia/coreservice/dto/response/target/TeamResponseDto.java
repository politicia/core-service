package com.politicia.coreservice.dto.response.target;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder @Getter @Setter
public class TeamResponseDto {

    private Long teamId;
    private String name;
    private String icon;
    private List<PlayerResponseDto> players;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
