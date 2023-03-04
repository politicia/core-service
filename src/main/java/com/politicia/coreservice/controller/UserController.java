package com.politicia.coreservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
//    private final UserServiceImpl userService;
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
//        UserResponseDto user = userService.getUser(id);
//        return ResponseEntity.ok().body(user);
//    }
//
//    @PostMapping
//    public ResponseEntity<Void> createUser(@RequestBody UserRequestDto userRequestDto) {
//        System.out.println(userRequestDto.getName());
//        System.out.println(userRequestDto.getNationality());
//        userService.createUser(userRequestDto);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }


}
