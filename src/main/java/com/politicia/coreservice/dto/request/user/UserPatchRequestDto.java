package com.politicia.coreservice.dto.request.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPatchRequestDto {
    @Nullable
    private String name;
    @Nullable
    private String nationality;
    @Nullable
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String profilePic;
}
