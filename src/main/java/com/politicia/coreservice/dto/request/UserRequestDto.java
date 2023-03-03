package com.politicia.coreservice.dto.request;

import com.politicia.coreservice.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class UserRequestDto {

    private String name;
    private String nationality;
    private String profilePic;

    public User toEntity() {
        return User.builder()
                .name(name)
                .nationality(nationality)
                .profilePic(profilePic)
                .build();
    }
}
