package com.politicia.coreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.politicia.coreservice.domain.Media;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.dto.request.media.MediaPostRequestDto;
import com.politicia.coreservice.dto.response.MediaResponseDto;
import com.politicia.coreservice.service.MediaService;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MediaController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class MediaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MediaService mediaService;

    @Test
    void testCreateMedia() throws Exception {

        MockMultipartFile testFile = new MockMultipartFile(
                "file",
                "hello.png",
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );
        MediaPostRequestDto postRequestDto = MediaPostRequestDto
                .builder()
                .postId(1L)
                .mediaType(com.politicia.coreservice.domain.MediaType.IMAGE)
                .build();
        MockMultipartFile jsonFile = new MockMultipartFile("body", "", "application/json", new ObjectMapper().writeValueAsString(postRequestDto).getBytes());

        mockMvc.perform(multipart("/media")
                .file(testFile)
                .file(jsonFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isCreated())
                .andDo(document("media-post",
                        requestParts(
                                partWithName("file").description("Media file to Upload"),
                                partWithName("body").description("JSON with post ID, media type")
                        )
                ));
        verify(mediaService, times(1)).createMedia(any(MediaPostRequestDto.class));
    }

    @Test
    void testDeleteMedia() throws Exception {

        mockMvc.perform(delete("/media/{mediaId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("media-delete",
                        pathParameters(
                                parameterWithName("mediaId").description("Media ID")
                )));
        verify(mediaService, times(1)).deleteMedia(1L);
    }

    @Test
    void testGetMedia() throws Exception {

        Post post = Post
                .builder()
                .id(1L)
                .title("title")
                .build();

        Media media1 = Media
                .builder()
                .id(1L)
                .post(post)
                .mediaType(com.politicia.coreservice.domain.MediaType.IMAGE)
                .src("https://profile.pic")
                .thumbnail("thumbnail")
                .build();
        Media media2 = Media
                .builder()
                .id(2L)
                .post(post)
                .mediaType(com.politicia.coreservice.domain.MediaType.IMAGE)
                .src("https://profile.pic")
                .thumbnail("thumbnail")
                .build();

        Page<MediaResponseDto> expected = new PageImpl<>(List.of(media1.toDto(), media2.toDto()));
        when(mediaService.getMediaListByPost(1L, 0)).thenReturn(expected);
        mockMvc.perform(get("/media/{postId}?page={page}", 1L, 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].mediaId").value(media1.getId()))
                .andExpect(jsonPath("$.content[1].mediaId").value(media2.getId()))
                .andDo(document("media-get",
                    pathParameters(
                            parameterWithName("postId").description("Post ID")
                    ),
                    queryParameters(
                            parameterWithName("page").description("Page number")
                    ),
                    responseFields(
                            fieldWithPath("content").type(JsonFieldType.ARRAY).description("List of Media"),
                            fieldWithPath("content[].mediaId").type(JsonFieldType.NUMBER).description("Media ID"),
                            fieldWithPath("content[].postId").type(JsonFieldType.NUMBER).description("Post ID of media"),
                            fieldWithPath("content[].mediaType").type(JsonFieldType.STRING).description("Media Type between IMAGE or VIDEO"),
                            fieldWithPath("content[].src").type(JsonFieldType.STRING).description("Media URL"),
                            fieldWithPath("content[].thumbnail").type(JsonFieldType.STRING).description("Media Thumbnail URL"),
                            fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("Media Creation Date"),
                            fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("Media Last Updated Date"),
                            fieldWithPath("pageable").type(JsonFieldType.STRING).description("Object storing Pageable response"),
                            fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("Indicates if this page is the last page"),
                            fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("Total number of pages available"),
                            fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("Total number of elements"),
                            fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("Indicates if this page is the first page"),
                            fieldWithPath("size").type(JsonFieldType.NUMBER).description("Maximum number of contents inside this page"),
                            fieldWithPath("number").type(JsonFieldType.NUMBER).description("Location of current page"),
                            fieldWithPath("sort").type(JsonFieldType.OBJECT).description("Object storing if the content is sorted or not"),
                            fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("True if the content is empty"),
                            fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("True if the content is unsorted"),
                            fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("True if the content is sorted"),
                            fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("Actual number of Elements in this content"),
                            fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("Indicates if the content is empty")
                    )
                ));
    }
}