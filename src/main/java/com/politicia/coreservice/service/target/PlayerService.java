package com.politicia.coreservice.service.target;

import com.politicia.coreservice.dto.request.target.player.PlayerPatchRequestDto;
import com.politicia.coreservice.dto.request.target.player.PlayerPostRequestDto;
import com.politicia.coreservice.dto.response.target.PlayerResponseDto;
import com.politicia.coreservice.repository.target.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public void createPlayer(PlayerPostRequestDto playerPostRequestDto) throws IOException {

    }

    public PlayerResponseDto getPlayerById(Long playerId) {
        return null;
    }

    public void editPlayerById(Long playerId, PlayerPatchRequestDto playerPatchRequestDto) throws IOException {

    }

    public void deletePlayerById(Long playerId) {

    }
}
