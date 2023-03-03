package com.politicia.coreservice.service;


import com.politicia.coreservice.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(com.politicia.coreservice.dto.request.UserRequestDto userRequestDto);

    UserResponseDto getUser(Long userId);

    List<UserResponseDto> getUserList();

//    void signOut();

//    void deleteUser(UserRequestDto userRequestDto);
}
