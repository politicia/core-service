package com.politicia.coreservice.dto.request.target.team;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Builder @Getter @Setter
public class TeamPatchRequestDto {

    @Nullable
    private String name;
    @Nullable
    private MultipartFile icon;

    @ConstructorProperties({"name", "icon"})
    public TeamPatchRequestDto(String name, MultipartFile icon) {
        this.name = name;
        this.icon = icon;
    }
}
