package com.politicia.coreservice.dto.request.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Getter @Setter
@Builder
public class UserPostRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String nationality;
    @Nullable
    private MultipartFile profilePic;

    @ConstructorProperties({"name", "nationality", "profilePic"})
    public UserPostRequestDto(String name, String nationality, MultipartFile profilePic) {
        this.name = name;
        this.nationality = nationality;
        this.profilePic = profilePic;
    }
}
