package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.target.player.PlayerPatchRequestDto;
import com.politicia.coreservice.dto.request.target.player.PlayerPostRequestDto;
import com.politicia.coreservice.dto.response.target.PlayerResponseDto;
import com.politicia.coreservice.service.target.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping
    public ResponseEntity<Void> postPlayer(@RequestPart MultipartFile file, @RequestPart PlayerPostRequestDto body) throws IOException{
        body.setIcon(file);
        playerService.createPlayer(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{playerId}")
    public ResponseEntity<Void> editPlayer(@PathVariable Long playerId, @RequestPart MultipartFile file, @RequestPart PlayerPatchRequestDto body) throws IOException {
        body.setIcon(file);
        playerService.editPlayerById(playerId, body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayerById(playerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
