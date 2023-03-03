package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.UserRequestDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void testSignUp() {
        //given
        UserRequestDto newUser = UserRequestDto.builder()
                .name("newUser")
                .profilePic("https://profile.pic")
                .nationality("korea")
                .build();
        User expectedUser = User.builder()
                .id(1L)
                .name("newUser")
                .nationality("korea")
                .profilePic("https://profile.pic")
                .build();

        //when
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        UserResponseDto actualUser = userService.createUser(newUser);

        // Verify
        Assertions.assertEquals(actualUser.getId(), expectedUser.getId());
        Assertions.assertEquals(actualUser.getName(), expectedUser.getName());
        Assertions.assertEquals(actualUser.getNationality(), expectedUser.getNationality());
        Assertions.assertEquals(actualUser.getProfilePic(), expectedUser.getProfilePic());
        Assertions.assertEquals(actualUser.getCreatedAt(), expectedUser.getCreatedAt());
        Assertions.assertEquals(actualUser.getUpdatedAt(), expectedUser.getUpdatedAt());
    }

    @Test
    void testGetUser() {
        //given
        User user = User.builder()
                .id(1L)
                .name("user")
                .nationality("korea")
                .profilePic("profilePic")
                .build();
        //when
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        UserResponseDto actualUserDto = userService.getUser(1L);
        //then
        Assertions.assertEquals(actualUserDto.getId(), user.getId());
        Assertions.assertEquals(actualUserDto.getName(), user.getName());
        Assertions.assertEquals(actualUserDto.getNationality(), user.getNationality());
        Assertions.assertEquals(actualUserDto.getProfilePic(), user.getProfilePic());
        Assertions.assertEquals(actualUserDto.getCreatedAt(), user.getCreatedAt());
        Assertions.assertEquals(actualUserDto.getUpdatedAt(), user.getUpdatedAt());

    }

    @Test
    void getUserList() {
        //given
        User userA = User.builder()
                .id(1L)
                .name("userA")
                .nationality("korea")
                .profilePic("profilePic1")
                .build();
        User userB = User.builder()
                .id(2L)
                .name("userB")
                .nationality("korea")
                .profilePic("profilePic2")
                .build();
        List<User> expectedUserList = new ArrayList<>(List.of(userA, userB));

        //when
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<UserResponseDto> actualUserDto = userService.getUserList();

        //then
        Assertions.assertEquals(actualUserDto.size(), 2);
        Assertions.assertEquals(actualUserDto.get(0).getName(), userA.getName());
        Assertions.assertEquals(actualUserDto.get(1).getName(), userB.getName());
    }
}