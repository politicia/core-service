package com.politicia.coreservice.dto.request.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Getter
public class UserPatchRequestDto {
    @Nullable
    private String name;
    @Nullable
    private String nationality;
    @Nullable
    private MultipartFile profilePic;

    @ConstructorProperties({"name", "nationality", "profilePic"})
    public UserPatchRequestDto(String name, String nationality, MultipartFile profilePic) {
        this.name = name;
        this.nationality = nationality;
        this.profilePic = profilePic;
    }
}
