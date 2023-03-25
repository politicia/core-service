package com.politicia.coreservice.dto.request.target.player;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Builder @Getter @Setter
public class PlayerPostRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private int age;
    @Nullable
    private Long teamId;
    @Nullable
    private MultipartFile icon;

    @ConstructorProperties({"name", "teamId", "icon", "age"})
    public PlayerPostRequestDto(String name, int age, Long teamId, MultipartFile icon) {
        this.name = name;
        this.age = age;
        this.teamId = teamId;
        this.icon = icon;
    }
}
