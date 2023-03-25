package com.politicia.coreservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class TargetResponseDto {
    private Long targetId;
    private String name;
    private String icon;
}
