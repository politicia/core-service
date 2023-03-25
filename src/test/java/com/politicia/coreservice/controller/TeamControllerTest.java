package com.politicia.coreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.politicia.coreservice.domain.target.Player;
import com.politicia.coreservice.domain.target.Team;
import com.politicia.coreservice.dto.request.target.team.TeamPatchRequestDto;
import com.politicia.coreservice.dto.request.target.team.TeamPostRequestDto;
import com.politicia.coreservice.service.target.TeamService;
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

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TeamController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Test
    void testGetTeamById() throws Exception {

        //given
        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("https://icon")
                .build();
        Player player1 = Player.builder()
                .id(1L)
                .name("player1")
                .age(20)
                .icon("icon")
                .team(team)
                .build();
        Player player2 = Player.builder()
                .id(2L)
                .name("player2")
                .age(21)
                .icon("icon2")
                .team(team)
                .build();
        team.getPlayerList().add(player1);
        team.getPlayerList().add(player2);
        //when
        when(teamService.getTeamById(1L)).thenReturn(team.toDto());
        //then
        mockMvc.perform(get("/team/{teamId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.teamId").value(team.getId()))
                .andExpect(jsonPath("$.name").value(team.getName()))
                .andExpect(jsonPath("$.icon").value(team.getIcon()))
                .andDo(document("team-get",
                        pathParameters(
                                parameterWithName("teamId").description("Team ID")
                        ),
                        responseFields(
                                fieldWithPath("teamId").type(JsonFieldType.NUMBER).description("Team ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Team name"),
                                fieldWithPath("icon").type(JsonFieldType.STRING).description("Team Icon URL"),
                                fieldWithPath("players").type(JsonFieldType.ARRAY).description("List of players on this team"),
                                fieldWithPath("players[].playerId").type(JsonFieldType.NUMBER).description("Player ID"),
                                fieldWithPath("players[].name").type(JsonFieldType.STRING).description("Player name"),
                                fieldWithPath("players[].icon").type(JsonFieldType.STRING).description("Player Icon URL"),
                                fieldWithPath("players[].age").type(JsonFieldType.NUMBER).description("Player age"),
                                fieldWithPath("players[].team").type(JsonFieldType.NULL).description("Team of Player, Empty on this response"),
                                fieldWithPath("players[].createdAt").type(JsonFieldType.STRING).description("Player Creation Date"),
                                fieldWithPath("players[].updatedAt").type(JsonFieldType.STRING).description("Player Last Updated Date"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Team Creation Date"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Team Last Updated Date")
                        )
                ));
    }

    @Test
    void testCreateTeam() throws Exception {
        //given
        MockMultipartFile icon = new MockMultipartFile("file", "file.png", MediaType.IMAGE_PNG_VALUE, "Image".getBytes());
        TeamPostRequestDto teamPostRequestDto = TeamPostRequestDto.builder()
                .name("teamname")
                .build();
        MockMultipartFile body = new MockMultipartFile("body", "", "application/json", new ObjectMapper().writeValueAsString(teamPostRequestDto).getBytes());
        //when
        //then
        mockMvc.perform(multipart("/team")
                .file(icon)
                .file(body)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isCreated())
                .andDo(document("team-post",
                        requestParts(
                                partWithName("body").description("JSON body"),
                                partWithName("file").description("New Icon image")
                        )
                ));
        verify(teamService, times(1)).createTeam(any(TeamPostRequestDto.class));
    }

    @Test
    void testEditTeam() throws Exception {
        //given
        TeamPatchRequestDto teamPatchRequestDto = TeamPatchRequestDto.builder()
                .name("new Name")
                .build();
        MockMultipartFile body = new MockMultipartFile("body", "", "application/json", new ObjectMapper().writeValueAsString(teamPatchRequestDto).getBytes());
        MockMultipartFile icon = new MockMultipartFile("file", "file.png", MediaType.IMAGE_PNG_VALUE, "Image".getBytes());
        //when
        //then
        mockMvc.perform(multipart("/team/{teamId}", 1L)
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
                .andDo(document("team-patch",
                        pathParameters(
                                parameterWithName("teamId").description("Team ID")
                        ),
                        requestParts(
                                partWithName("body").description("JSON body"),
                                partWithName("file").description("New Icon image")
                        )));
        verify(teamService, times(1)).editTeamById(any(Long.class), any(TeamPatchRequestDto.class));
    }

    @Test
    void testDeleteTeam() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(delete("/team/{teamId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("team-delete",
                        pathParameters(
                                parameterWithName("teamId").description("Team ID")
                        )
                        ));
        verify(teamService, times(1)).deleteTeamById(1L);
    }
}