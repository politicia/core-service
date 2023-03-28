package com.politicia.coreservice.controller;

import com.politicia.coreservice.dto.request.target.team.TeamPatchRequestDto;
import com.politicia.coreservice.dto.request.target.team.TeamPostRequestDto;
import com.politicia.coreservice.dto.response.target.TeamResponseDto;
import com.politicia.coreservice.service.target.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> getTeamById(@PathVariable Long teamId) {
        TeamResponseDto teamResponseDto = teamService.getTeamById(teamId);
        return ResponseEntity.ok().body(teamResponseDto);
    }

    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestPart MultipartFile file, @RequestPart TeamPostRequestDto body) throws IOException {
        body.setIcon(file);
        teamService.createTeam(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity<Void> editTeam(@PathVariable Long teamId, @RequestPart MultipartFile file, @RequestPart TeamPatchRequestDto body) throws IOException {
        body.setIcon(file);
        teamService.editTeamById(teamId, body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeamById(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
