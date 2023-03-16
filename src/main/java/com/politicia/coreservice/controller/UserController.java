package com.politicia.coreservice.controller;

import com.politicia.coreservice.domain.MediaType;
import com.politicia.coreservice.dto.request.media.MediaPostRequestDto;
import com.politicia.coreservice.dto.request.user.UserPostRequestDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.service.MediaService;
import com.politicia.coreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto user = userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestPart MultipartFile file, @RequestPart UserPostRequestDto body) {
        try {
            body.setProfilePic(file);
            userService.createUser(body);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
