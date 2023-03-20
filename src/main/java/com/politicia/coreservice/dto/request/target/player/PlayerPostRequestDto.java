package com.politicia.coreservice.dto.request.target.player;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Builder @Getter @Setter
public class PlayerPostRequestDto {
    @NotBlank
    private String name;
    @Nullable
    private Long teamId;
    @Nullable
    private MultipartFile icon;

    @ConstructorProperties({"name", "teamId", "icon"})
    public PlayerPostRequestDto(String name, Long teamId, MultipartFile icon) {
        this.name = name;
        this.teamId = teamId;
        this.icon = icon;
    }
}
