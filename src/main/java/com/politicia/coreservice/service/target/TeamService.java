package com.politicia.coreservice.service.target;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.politicia.coreservice.domain.target.Team;
import com.politicia.coreservice.dto.request.target.team.TeamPatchRequestDto;
import com.politicia.coreservice.dto.request.target.team.TeamPostRequestDto;
import com.politicia.coreservice.dto.response.target.TeamResponseDto;
import com.politicia.coreservice.repository.target.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySource(("aws.yaml"))
public class TeamService {
    private final TeamRepository teamRepository;
    private final AmazonS3 amazonS3;
    @Value("${MEDIA_BUCKET_NAME}")
    private String mediaBucket;
    @Value("${CLOUDFRONT_MEDIA_URL_PREFIX}")
    private String mediaPrefix;

    private String putObject(MultipartFile file, String teamName) throws  IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        InputStream inputStream = file.getInputStream();
        String key = String.format("%s/%s-%s-%s", "team_icon", UUID.randomUUID().toString(), teamName, file.getOriginalFilename());
        amazonS3.putObject(mediaBucket, key, inputStream, objectMetadata);
        return mediaPrefix + '/' + key;
    }

    public void createTeam(TeamPostRequestDto teamPostRequestDto) throws IOException {
        String key = putObject(teamPostRequestDto.getIcon(), teamPostRequestDto.getName());
        Team team = Team.builder()
                .name(teamPostRequestDto.getName())
                .icon(key)
                .build();
        teamRepository.save(team);
    }

    public TeamResponseDto getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .get()
                .toDto();
    }

    public void editTeamById(Long teamId, TeamPatchRequestDto teamPatchRequestDto) throws IOException {
        Team team = teamRepository.findById(teamId).get();

        if (!teamPatchRequestDto.getName().isBlank()) {
            team.setName(teamPatchRequestDto.getName());
        }
        if (!teamPatchRequestDto.getIcon().isEmpty()) {
            String newSrc = putObject(teamPatchRequestDto.getIcon(), team.getName());
            amazonS3.deleteObject(mediaBucket, team.getIcon().replace(mediaPrefix + '/', ""));
            team.setIcon(newSrc);
        }
    }

    public void deleteTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).get();
        amazonS3.deleteObject(mediaBucket, team.getIcon().replace(mediaPrefix + '/', ""));
        teamRepository.delete(team);
    }
}
