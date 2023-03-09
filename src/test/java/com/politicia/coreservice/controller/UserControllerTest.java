package com.politicia.coreservice.controller;

import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.user.UserPostRequestDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUser() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .name("test")
                .profilePic("https://profile.pic")
                .nationality("testCountry")
                .build();

        //when
        when(userService.getUser(1L)).thenReturn(user.toDto());
        //then

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.profilePic").value(user.getProfilePic()))
                .andExpect(jsonPath("$.nationality").value(user.getNationality()));

    }

    @Test
    void createUser() throws Exception {
        //given
        UserPostRequestDto userRequestDto = UserPostRequestDto.builder()
                .name("test")
                .profilePic("https://profile.pic")
                .nationality("testCountry")
                .build();
        UserResponseDto expectedUser = UserResponseDto.builder()
                .id(1L)
                .name("test")
                .profilePic("https://profile.pic")
                .nationality("testCountry")
                .build();

        //when
        when(userService.createUser(userRequestDto)).thenReturn(expectedUser);
        //then
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "    \"name\": \"test\",\n" +
                                "    \"nationality\": \"testCountry\",\n" +
                                "    \"profilePic\": \"https://profile.pic\"\n" +
                                "}"
                        ))
                .andExpect(status().isCreated());

    }
}