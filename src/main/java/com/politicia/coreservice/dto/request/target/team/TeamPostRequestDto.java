package com.politicia.coreservice.dto.request.target.team;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Getter @Setter @Builder
public class TeamPostRequestDto {

    @NotBlank
    private String name;
    @Nullable
    private MultipartFile icon;

    @ConstructorProperties({"name", "icon"})
    public TeamPostRequestDto(String name, MultipartFile icon) {
        this.name = name;
        this.icon = icon;
    }
}
