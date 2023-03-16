package com.politicia.coreservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.user.UserPostRequestDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("aws.yaml")
public class UserService {

    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;
    @Value("${MEDIA_BUCKET_NAME}")
    private String mediaBucket;
    public UserResponseDto createUser(UserPostRequestDto userRequestDto) throws IOException {

        //upload media and get url
        String fileName = userRequestDto.getProfilePic().getName();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        String src = String.format("%s/%s-%s.%s", "PROFILE_PIC", UUID.randomUUID().toString(), userRequestDto.getName(), ext);
        File file = new File("file");
        userRequestDto.getProfilePic().transferTo(file);
        amazonS3.putObject(mediaBucket, src, file);

        User user = User.builder()
                .name(userRequestDto.getName())
                .nationality(userRequestDto.getNationality())
                .profilePic(src)
                .build();
        User newUser = userRepository.save(user);
        file.delete();
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
