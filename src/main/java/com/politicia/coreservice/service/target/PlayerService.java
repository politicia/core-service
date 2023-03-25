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
public class PlayerService {
    private final PlayerRepository playerRepository;
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
        String key = String.format("%s/%s-%s-%s", "player_icon", UUID.randomUUID().toString(), teamName, file.getOriginalFilename());
        amazonS3.putObject(mediaBucket, key, inputStream, objectMetadata);
        return mediaPrefix + '/' + key;
    }

    public void createPlayer(PlayerPostRequestDto playerPostRequestDto) throws IOException {
        String key = putObject(playerPostRequestDto.getIcon(), playerPostRequestDto.getName());
        Team team = teamRepository.findById(playerPostRequestDto.getTeamId()).get();
        Player player = Player.builder()
                .name(playerPostRequestDto.getName())
                .team(team)
                .age(playerPostRequestDto.getAge())
                .icon(key)
                .build();
        playerRepository.save(player);

    }

    public PlayerResponseDto getPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .get()
                .toDetailDto();
    }

    public void editPlayerById(Long playerId, PlayerPatchRequestDto playerPatchRequestDto) throws IOException {
        Player player = playerRepository.findById(playerId).get();

        if (!playerPatchRequestDto.getName().isBlank()) {
            player.setName(playerPatchRequestDto.getName());
        }
        if (playerPatchRequestDto.getAge() != null) {
            player.setAge(playerPatchRequestDto.getAge());
        }
        if (playerPatchRequestDto.getTeamId() != null) {
            Team team = teamRepository.findById(playerPatchRequestDto.getTeamId()).get();
            player.setTeam(team);
        }
        if (!playerPatchRequestDto.getIcon().isEmpty()) {
            String newSrc = putObject(playerPatchRequestDto.getIcon(), player.getName());
            amazonS3.deleteObject(mediaBucket, player.getIcon().replace(mediaPrefix + '/', ""));
            player.setIcon(newSrc);
        }
    }

    public void deletePlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        amazonS3.deleteObject(mediaBucket, player.getIcon().replace(mediaPrefix + '/', ""));
        playerRepository.delete(player);
    }
}
