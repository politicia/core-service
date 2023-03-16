package com.politicia.coreservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
import java.io.InputStream;
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
    @Value("${CLOUDFRONT_MEDIA_URL_PREFIX}")
    private String mediaPrefix;
    public UserResponseDto createUser(UserPostRequestDto userRequestDto) throws IOException {

        //upload media and get url

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(userRequestDto.getProfilePic().getContentType());
        InputStream inputStream = userRequestDto.getProfilePic().getInputStream();
        String key = String.format("%s/%s-%s-%s", "PROFILE_PIC", UUID.randomUUID().toString(), userRequestDto.getName(), userRequestDto.getProfilePic().getOriginalFilename());
        amazonS3.putObject(mediaBucket, key, inputStream, objectMetadata);
        User user = User.builder()
                .name(userRequestDto.getName())
                .nationality(userRequestDto.getNationality())
                .profilePic(mediaPrefix + '/' + key)
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
