package com.politicia.coreservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.politicia.coreservice.domain.Media;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.dto.request.media.MediaPostRequestDto;
import com.politicia.coreservice.dto.response.MediaResponseDto;
import com.politicia.coreservice.repository.MediaRepository;
import com.politicia.coreservice.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("aws.yaml")
public class MediaService {


    private final AmazonS3 amazonS3;
    private final MediaRepository mediaRepository;
    private final PostRepository postRepository;
    @Value("${MEDIA_BUCKET_NAME}")
    private String mediaBucket;

    public void createMedia(MediaPostRequestDto mediaPostRequestDto) throws IOException {
        Post post = postRepository.findById(mediaPostRequestDto.getPostId()).get();
        Media media = Media.builder()
                .post(post)
                .mediaType(mediaPostRequestDto.getMediaType())
                .build();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(mediaPostRequestDto.getFile().getContentType());
        String fileName = mediaPostRequestDto.getFile().getName();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        String key = String.format("%s/%s-%s.%s", media.getMediaType(), UUID.randomUUID().toString(), media.getCreatedAt(), ext);

        InputStream inputStream = mediaPostRequestDto.getFile().getInputStream();
        media.setSrc(key);
        amazonS3.putObject(mediaBucket, key, inputStream, objectMetadata);
        mediaRepository.save(media);
    }
    public void deleteMedia(Long mediaId) {
        Media media = mediaRepository.findById(mediaId).get();
        amazonS3.deleteObject(new DeleteObjectRequest(mediaBucket, media.getSrc()));
        mediaRepository.delete(media);
    }
    public Page<MediaResponseDto> getMediaListByPost(Long postId, int page) {
        PageRequest pr = PageRequest.of(page, 10);
        Post post = postRepository.findById(postId).get();
        Page<Media> mediaList = mediaRepository.findByPost(post, pr);
        return mediaList.map(Media::toDto);
    }
}
