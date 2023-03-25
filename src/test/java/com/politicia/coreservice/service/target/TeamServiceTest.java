package com.politicia.coreservice.service.target;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.politicia.coreservice.domain.target.Player;
import com.politicia.coreservice.domain.target.Team;
import com.politicia.coreservice.dto.request.target.team.TeamPatchRequestDto;
import com.politicia.coreservice.dto.request.target.team.TeamPostRequestDto;
import com.politicia.coreservice.dto.response.target.TeamResponseDto;
import com.politicia.coreservice.repository.target.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    TeamService teamService;

    @Mock
    TeamRepository teamRepository;

    @Mock
    AmazonS3 amazonS3;

    @Test
    void testCreateTeam() throws IOException {
        ReflectionTestUtils.setField(teamService, "mediaBucket", "MEDIA_BUCKET");
        //given
        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("https://icon")
                .build();
        MultipartFile file = new MockMultipartFile("file.txt", new byte[0]);
        TeamPostRequestDto teamPostRequestDto = TeamPostRequestDto.builder()
                .name("team")
                .icon(file)
                .build();
        //when
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        teamService.createTeam(teamPostRequestDto);

        //then
        verify(amazonS3, times(1)).putObject(any(String.class), any(String.class), any(InputStream.class), any(ObjectMetadata.class));
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testGetTeamById() {
        //given
        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("https://icon")
                .build();

        TeamResponseDto expectedDto = team.toDto();
        //when
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        TeamResponseDto teamResponseDto = teamService.getTeamById(1L);
        //then
        assertEquals(teamResponseDto.getTeamId(), expectedDto.getTeamId());
        assertEquals(teamResponseDto.getIcon(), expectedDto.getIcon());
        assertEquals(teamResponseDto.getName(), expectedDto.getName());
        assertEquals(teamResponseDto.getCreatedAt(), expectedDto.getCreatedAt());
        assertEquals(teamResponseDto.getUpdatedAt(), expectedDto.getUpdatedAt());
    }

    @Test
    void testEditTeamById() throws IOException {
        //given
        ReflectionTestUtils.setField(teamService, "mediaBucket", "MEDIA_BUCKET");
        ReflectionTestUtils.setField(teamService, "mediaPrefix", "https://test.url");

        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("https://icon")
                .build();
        MultipartFile newIcon = new MockMultipartFile("file.txt", new byte[100]);
        TeamPatchRequestDto teamPatchRequestDto = TeamPatchRequestDto.builder()
                .name("newTeam")
                .icon(newIcon)
                .build();

        //when
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        teamService.editTeamById(1L, teamPatchRequestDto);

        //then
        assertEquals(team.getName(), "newTeam");
        verify(teamRepository, times(1)).findById(1L);
        verify(amazonS3, times(1)).putObject(any(String.class), any(String.class), any(InputStream.class), any(ObjectMetadata.class));
        verify(amazonS3, times(1)).deleteObject(any(String.class), any(String.class));
    }

    @Test
    void testDeleteTeamById() {
        //given
        ReflectionTestUtils.setField(teamService, "mediaBucket", "MEDIA_BUCKET");
        Team team = Team.builder()
                .id(1L)
                .name("name")
                .icon("icon")
                .build();
        //when
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        teamService.deleteTeamById(1L);
        //then
        verify(amazonS3, times(1)).deleteObject(any(String.class), any(String.class));
        verify(teamRepository, times(1)).delete(any(Team.class));
    }
}