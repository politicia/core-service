package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.user.UserPostRequestDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserPostRequestDto userRequestDto) {

        //upload media and get url
        String profilePicUrl = "";

        User user = User.builder()
                .name(userRequestDto.getName())
                .nationality(userRequestDto.getNationality())
                .profilePic(profilePicUrl)
                .build();
        User newUser = userRepository.save(user);
        return UserResponseDto.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .nationality(newUser.getNationality())
                .profilePic(newUser.getProfilePic())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }
    public UserResponseDto getUser(Long userId)  {
        User foundUser = userRepository.findById(userId).get();
        return UserResponseDto.builder()
                .id(foundUser.getId())
                .name(foundUser.getName())
                .nationality(foundUser.getNationality())
                .profilePic(foundUser.getProfilePic())
                .createdAt(foundUser.getCreatedAt())
                .updatedAt(foundUser.getUpdatedAt())
                .build();
    }

    public List<UserResponseDto> getUserList() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> result = new ArrayList<>();
        for (User user: users) {
            result.add(UserResponseDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .nationality(user.getNationality())
                    .profilePic(user.getProfilePic())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        }
        return result;
    }
}
