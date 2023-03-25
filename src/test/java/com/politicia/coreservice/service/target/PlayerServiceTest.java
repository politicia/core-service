package com.politicia.coreservice.service.target;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.politicia.coreservice.domain.target.Player;
import com.politicia.coreservice.domain.target.Team;
import com.politicia.coreservice.dto.request.target.player.PlayerPatchRequestDto;
import com.politicia.coreservice.dto.request.target.player.PlayerPostRequestDto;
import com.politicia.coreservice.dto.response.target.PlayerResponseDto;
import com.politicia.coreservice.repository.target.PlayerRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @InjectMocks
    PlayerService playerService;
    @Mock
    PlayerRepository playerRepository;
    @Mock
    TeamRepository teamRepository;
    @Mock
    AmazonS3 amazonS3;

    @Test
    void testCreatePlayer() throws IOException {
        ReflectionTestUtils.setField(playerService, "mediaBucket", "MEDIA_BUCKET");
        //given
        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("https://icon")
                .build();
        MultipartFile file = new MockMultipartFile("file.txt", new byte[0]);
        Player player = Player.builder()
                .id(1L)
                .name("player")
                .icon("https://icon")
                .team(team)
                .build();
        PlayerPostRequestDto playerPostRequestDto = PlayerPostRequestDto.builder()
                .name("player")
                .teamId(team.getId())
                .icon(file)
                .build();

        //when
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        playerService.createPlayer(playerPostRequestDto);

        //then
        verify(amazonS3, times(1)).putObject(any(String.class), any(String.class), any(InputStream.class), any(ObjectMetadata.class));
        verify(playerRepository, times(1)).save(any(Player.class));

    }
    @Test
    void testGetPlayerById() {
        //given
        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("https://icon")
                .build();
        Player player = Player.builder()
                .id(1L)
                .name("player")
                .icon("https://icon")
                .team(team)
                .build();
        PlayerResponseDto expectedDto = player.toDetailDto();
        //when
        when(playerRepository.findById(any(Long.class))).thenReturn(Optional.of(player));
        PlayerResponseDto playerResponseDto = playerService.getPlayerById(1L);
        //then
        assertEquals(playerResponseDto.getPlayerId(), expectedDto.getPlayerId());
        assertEquals(playerResponseDto.getAge(), expectedDto.getAge());
        assertEquals(playerResponseDto.getName(), expectedDto.getName());
        assertEquals(playerResponseDto.getTeam().getTeamId(), expectedDto.getTeam().getTeamId());
        assertEquals(playerResponseDto.getTeam().getName(), expectedDto.getTeam().getName());
        assertEquals(playerResponseDto.getTeam().getIcon(), expectedDto.getTeam().getIcon());
        assertEquals(playerResponseDto.getTeam().getCreatedAt(), expectedDto.getTeam().getCreatedAt());
        assertEquals(playerResponseDto.getTeam().getUpdatedAt(), expectedDto.getTeam().getUpdatedAt());
        assertEquals(playerResponseDto.getIcon(), expectedDto.getIcon());
        assertEquals(playerResponseDto.getCreatedAt(), expectedDto.getCreatedAt());
        assertEquals(playerResponseDto.getUpdatedAt(), expectedDto.getUpdatedAt());
    }
    @Test
    void testEditPlayerById() throws IOException {
        ReflectionTestUtils.setField(playerService, "mediaBucket", "MEDIA_BUCKET");
        //given
        Team team = Team.builder()
                .id(1L)
                .name("team")
                .icon("https://icon")
                .build();
        Player player = Player.builder()
                .id(1L)
                .name("player")
                .age(22)
                .icon("https://icon")
                .team(team)
                .build();
        MultipartFile newIcon = new MockMultipartFile("file.txt", new byte[100]);
        PlayerPatchRequestDto playerPatchRequestDto = PlayerPatchRequestDto.builder()
                .name("name")
                .age(20)
                .icon(newIcon)
                .build();
        //when
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        playerService.editPlayerById(1L, playerPatchRequestDto);

        //then
        assertEquals(player.getName(), "name");
        assertEquals(player.getAge(), playerPatchRequestDto.getAge());
        verify(playerRepository, times(1)).findById(1L);
        verify(amazonS3, times(1)).putObject(any(String.class), any(String.class), any(InputStream.class), any(ObjectMetadata.class));
        verify(amazonS3, times(1)).deleteObject(any(String.class), any(String.class));

    }
    @Test
    void testDeletePlayerById() {
        ReflectionTestUtils.setField(playerService, "mediaBucket", "MEDIA_BUCKET");
        //given
        Player player = Player.builder()
                .id(1L)
                .name("player name")
                .age(20)
                .icon("icon")
                .build();
        //when
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        playerService.deletePlayerById(1L);
        //then
        verify(amazonS3, times(1)).deleteObject(any(String.class), any(String.class));
        verify(playerRepository, times(1)).delete(any(Player.class));

    }
}