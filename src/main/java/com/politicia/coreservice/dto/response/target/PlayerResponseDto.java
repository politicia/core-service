package com.politicia.coreservice.dto.response.target;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder @Getter @Setter
public class PlayerResponseDto {

    private Long playerId;
    private String name;
    private String icon;
    private int age;
    private TeamResponseDto team;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
