package com.politicia.coreservice.dto.request.target.player;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;
import java.util.Optional;

@Builder @Getter @Setter
public class PlayerPatchRequestDto {

    @Nullable
    private String name;
    @Nullable
    private Integer age;
    @Nullable
    private Long teamId;
    @Nullable
    private MultipartFile icon;

    @ConstructorProperties({"teamId", "age", "name", "icon"})
    public PlayerPatchRequestDto(String name, Integer age, Long teamId, MultipartFile icon) {
        this.name = name;
        this.age = age;
        this.teamId = teamId;
        this.icon = icon;
    }
}
