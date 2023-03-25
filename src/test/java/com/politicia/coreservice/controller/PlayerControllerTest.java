package com.politicia.coreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.politicia.coreservice.domain.target.Player;
import com.politicia.coreservice.domain.target.Team;
import com.politicia.coreservice.dto.request.target.player.PlayerPatchRequestDto;
import com.politicia.coreservice.dto.request.target.player.PlayerPostRequestDto;
import com.politicia.coreservice.dto.request.target.team.TeamPatchRequestDto;
import com.politicia.coreservice.dto.response.target.PlayerResponseDto;
import com.politicia.coreservice.service.target.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    void testGetPlayerById() throws Exception {
        //given
        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("team icon")
                .build();

        PlayerResponseDto player = Player.builder()
                .id(1L)
                .icon("https://icon")
                .name("player name")
                .age(30)
                .team(team)
                .build()
                .toDetailDto();
        //when
        when(playerService.getPlayerById(1L)).thenReturn(player);
        //then
        mockMvc.perform(get("/player/{playerId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.playerId").value(player.getPlayerId()))
                .andExpect(jsonPath("$.name").value(player.getName()))
                .andExpect(jsonPath("$.icon").value(player.getIcon()))
                .andExpect(jsonPath("$.age").value(player.getAge()))
                .andDo(document("player-get",
                        pathParameters(
                                parameterWithName("playerId").description("Player ID")
                        ),
                        responseFields(
                                fieldWithPath("playerId").type(JsonFieldType.NUMBER).description("Player ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Player Name"),
                                fieldWithPath("icon").type(JsonFieldType.STRING).description("Player Icon URL"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("Player Age"),
                                fieldWithPath("team").type(JsonFieldType.OBJECT).description("Player Team"),
                                fieldWithPath("team.teamId").type(JsonFieldType.NUMBER).description("Team ID"),
                                fieldWithPath("team.name").type(JsonFieldType.STRING).description("Team name"),
                                fieldWithPath("team.icon").type(JsonFieldType.STRING).description("Team Icon URL"),
                                fieldWithPath("team.players").type(JsonFieldType.NULL).description("Players in team, null in this response"),
                                fieldWithPath("team.createdAt").type(JsonFieldType.STRING).description("Team Creation Date"),
                                fieldWithPath("team.updatedAt").type(JsonFieldType.STRING).description("Team Last Updated Date"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Player Creation Date"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Player Last Updated Date")
                        )
                        ));

        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    void testPostPlayer() throws Exception {
        //given
        MockMultipartFile icon = new MockMultipartFile("file", "file.png", MediaType.IMAGE_PNG_VALUE, "Image".getBytes());
        PlayerPostRequestDto playerPostRequestDto = PlayerPostRequestDto
                .builder()
                .name("player name")
                .teamId(1L)
                .build();
        MockMultipartFile body = new MockMultipartFile("body", "", "application/json", new ObjectMapper().writeValueAsString(playerPostRequestDto).getBytes());
        //when
        //then
        mockMvc.perform(multipart("/player")
                        .file(icon)
                        .file(body)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isCreated())
                .andDo(document("player-post",
                        requestParts(
                                partWithName("body").description("JSON body"),
                                partWithName("file").description("New Icon image")
                        )
                ));
        verify(playerService, times(1)).createPlayer(any(PlayerPostRequestDto.class));
    }

    @Test
    void testEditPlayer() throws Exception {
        //given
        MockMultipartFile icon = new MockMultipartFile("file", "file.png", MediaType.IMAGE_PNG_VALUE, "Image".getBytes());
        PlayerPatchRequestDto playerPatchRequestDto = PlayerPatchRequestDto.builder()
                .name("new player name")
                .teamId(2L)
                .age(20)
                .build();
        MockMultipartFile body = new MockMultipartFile("body", "", "application/json", new ObjectMapper().writeValueAsString(playerPatchRequestDto).getBytes());

        //then
        mockMvc.perform(multipart("/player/{playerId}", 1L)
                        .file(icon)
                        .file(body)
                        .with(new RequestPostProcessor() {
                            @Override
                            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                request.setMethod("PATCH");
                                return request;
                            }
                        })
                )
                .andExpect(status().isOk())
                .andDo(document("player-patch",
                        pathParameters(
                                parameterWithName("playerId").description("Player ID")
                        ),
                        requestParts(
                                partWithName("body").description("JSON body"),
                                partWithName("file").description("New Icon image")
                        )));
        verify(playerService, times(1)).editPlayerById(any(Long.class), any(PlayerPatchRequestDto.class));
    }

    @Test
    void testDeletePlayer() throws Exception {
        mockMvc.perform(delete("/player/{playerId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("player-delete",
                        pathParameters(
                                parameterWithName("playerId").description("Player ID")
                        )
                ));
        verify(playerService, times(1)).deletePlayerById(1L);
    }
}