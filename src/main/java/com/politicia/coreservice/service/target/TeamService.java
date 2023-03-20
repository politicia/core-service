package com.politicia.coreservice.service.target;

import com.politicia.coreservice.dto.request.target.team.TeamPatchRequestDto;
import com.politicia.coreservice.dto.request.target.team.TeamPostRequestDto;
import com.politicia.coreservice.dto.response.target.TeamResponseDto;
import com.politicia.coreservice.repository.target.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public void createTeam(TeamPostRequestDto teamPostRequestDto) {

    }

    public TeamResponseDto getTeamById(Long teamId) {
        return null;
    }

    public void editTeamById(Long teamId, TeamPatchRequestDto teamPatchRequestDto) {

    }

    public void deleteTeamById(Long teamId) {

    }
}
