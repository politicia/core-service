package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.response.target.PlayerResponseDto;
import com.politicia.coreservice.service.target.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerResponseDto> getPlayerById(@PathVariable Long playerId) {
        PlayerResponseDto playerResponseDto = playerService.getPlayerById(playerId);
        return ResponseEntity.ok().body(playerResponseDto);
    }


}
