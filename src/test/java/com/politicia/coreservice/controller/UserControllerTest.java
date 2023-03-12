package com.politicia.coreservice.controller;

import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.user.UserPostRequestDto;
import com.politicia.coreservice.dto.response.UserResponseDto;
import com.politicia.coreservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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

        mockMvc.perform(RestDocumentationRequestBuilders.get("/user/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.profilePic").value(user.getProfilePic()))
                .andExpect(jsonPath("$.nationality").value(user.getNationality()))
                .andDo(document("user-get",
                        pathParameters(
                                parameterWithName("userId").description("User ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("User ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Username"),
                                fieldWithPath("nationality").type(JsonFieldType.STRING).description("User's Nationality"),
                                fieldWithPath("profilePic").type(JsonFieldType.STRING).description("User's Profile Image"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("User Creation Date"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("User Last Updated Date")
                        )
                ));

    }

    @Test
    void createUser() throws Exception {
        //given
        MultipartFile file = new MockMultipartFile("name.txt", new byte[0]);
        UserPostRequestDto userRequestDto = UserPostRequestDto.builder()
                .name("test")
                .profilePic(file)
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
        mockMvc.perform(RestDocumentationRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "    \"name\": \"test\",\n" +
                                "    \"nationality\": \"testCountry\",\n" +
                                "    \"profilePic\": \"https://profile.pic\"\n" +
                                "}"
                        ))
                .andExpect(status().isCreated())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("New Username"),
                                fieldWithPath("nationality").type(JsonFieldType.STRING).description("New nationality"),
                                fieldWithPath("profilePic").type(JsonFieldType.STRING).description("New Profile image")
                        )
                ));

    }
}