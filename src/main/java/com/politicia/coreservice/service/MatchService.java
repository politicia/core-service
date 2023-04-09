package com.politicia.coreservice.service;

import com.politicia.coreservice.dto.request.match.MatchPatchRequestDto;
import com.politicia.coreservice.dto.request.match.MatchPostRequestDto;
import com.politicia.coreservice.dto.response.MatchResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MatchService {
    public MatchResponseDto getMatchById(Long matchId) {
        return null;
    }

    public List<MatchResponseDto> getMatchesByDate(LocalDate date) {
        return null;
    }

    public void editMatch(MatchPatchRequestDto matchPatchRequestDto) {

    }

    public void createMatch(MatchPostRequestDto matchPostRequestDto) {

    }

    public void deleteMatch(Long matchId) {

    }
}
