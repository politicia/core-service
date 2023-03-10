package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.user.UserPostRequestDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> createUser(UserPostRequestDto userRequestDto) {
        userService.createUser(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
