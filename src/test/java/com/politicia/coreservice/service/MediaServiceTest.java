package com.politicia.coreservice.service;

import com.politicia.coreservice.domain.Media;
import com.politicia.coreservice.domain.MediaType;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.dto.request.MediaRequestDto;
import com.politicia.coreservice.dto.response.MediaResponseDto;
import com.politicia.coreservice.repository.MediaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @InjectMocks
    private MediaService mediaService;
    @Mock
    private MediaRepository mediaRepository;

    @Test
    void testCreateMedia() {
        //given
        Media media = Media.builder()
                .id(1L)
                .src("https://media.src")
                .mediaType(MediaType.IMAGE)
                .build();

        MediaRequestDto mediaRequestDto = MediaRequestDto.builder()
                .mediaType(MediaType.IMAGE)
                .build();

        //when
        when(mediaRepository.save(any(Media.class))).thenReturn(media);
        mediaService.createMedia(mediaRequestDto);
        //then
        verify(mediaRepository, times(1)).save(any(Media.class));
    }

    @Test
    void testDeleteMedia() {
        //given
        Media media = Media.builder()
                .id(1L)
                .src("https://media.src")
                .mediaType(MediaType.IMAGE)
                .build();
        Long mediaId = 1L;
        //when
        when(mediaRepository.findById(1L)).thenReturn(Optional.of(media));
        mediaService.deleteMedia(mediaId);
        //then
        verify(mediaRepository, times(1)).findById(any(Long.class));
        verify(mediaRepository, times(1)).delete(any(Media.class));
    }

    @Test
    void testGetMediaListByPost() {
        //given
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .build();
        Media mediaA = Media.builder()
                .id(1L)
                .post(post)
                .src("https://media.src")
                .mediaType(MediaType.IMAGE)
                .build();
        Media mediaB = Media.builder()
                .id(2L)
                .post(post)
                .src("https://media2.src")
                .mediaType(MediaType.VIDEO)
                .build();
        Page<Media> mediaPage = new PageImpl<>(new ArrayList<>(List.of(mediaA, mediaB)));

        //when
        when(mediaRepository.findByPost(any(Post.class))).thenReturn(mediaPage);
        Page<MediaResponseDto> result = mediaService.getMediaListByPost(post.getId());
        //then
        Assertions.assertEquals(result.getContent().get(0).getMediaId(), mediaA.getId());
        Assertions.assertEquals(result.getContent().get(1).getMediaId(), mediaB.getId());
    }
}