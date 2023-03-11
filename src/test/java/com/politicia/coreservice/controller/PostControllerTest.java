package com.politicia.coreservice.controller;

import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.post.PostPatchRequestDto;
import com.politicia.coreservice.dto.request.post.PostPostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    void createPost() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .name("test")
                .nationality("country")
                .profilePic("https://profile.pic")
                .build();
        PostPatchRequestDto requestDto = PostPatchRequestDto.builder()
                .title("title")
                .text("text")
                .build();

        //then
        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("""
                                {
                                    "userId": 1,
                                    "title": "text",
                                    "text": "text"
                                }"""
                        ))
                .andExpect(status().isCreated())
                .andDo(document("post-post",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("text").type(JsonFieldType.STRING).description("Text")
                        )
                        ));
        verify(postService, times(1)).createPost(any(PostPostRequestDto.class));
    }

    @Test
    void editPost() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .name("test")
                .nationality("country")
                .profilePic("https://profile.pic")
                .build();
        PostPatchRequestDto requestDto = PostPatchRequestDto.builder()
                .title("title")
                .text("text")
                .build();
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .postId(1L)
                .user(user.toDto())
                .title("titleEdited")
                .text("textEdited")
                .build();

        //when
        mockMvc.perform(patch("/post/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("""
                        {
                            "title": "titleEdited",
                            "text": "textEdited"
                        }"""
                ))
                .andExpect(status().isOk())
                .andDo(document("post-patch",
                            pathParameters(
                                    parameterWithName("postId").description("Post ID")
                            ),
                            requestFields(
                                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                    fieldWithPath("text").type(JsonFieldType.STRING).description("Text")
                            )
                ));
        verify(postService, times(1)).editPost(any(Long.class), any(PostPatchRequestDto.class));

    }

    @Test
    void deletePost() throws Exception {
        //given
        Long postId = 1L;
        //when

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/post/{postId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("post-delete",
                    pathParameters(
                            parameterWithName("postId").description("Post ID")
                    )
                ));
        verify(postService, times(1)).deletePost(postId);
    }

    @Test
    void getPost() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .name("test")
                .nationality("test")
                .profilePic("https://profile.pic")
                .build();
        PostResponseDto expectedDto = Post.builder()
                .id(1L)
                .user(user)
                .title("title")
                .text("text")
                .build()
                .toDto();

        expectedDto.setMediaList(new ArrayList<>());
        //when
        when(postService.getPostById(1L)).thenReturn(expectedDto);
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/post/{postId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(expectedDto.getPostId()))
                .andExpect(jsonPath("$.title").value(expectedDto.getTitle()))
                .andDo(document("post-get-single",
                        pathParameters(
                                parameterWithName("postId").description("Post ID")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("Post ID"),
                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("User Info"),
                                fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("User ID"),
                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("Username"),
                                fieldWithPath("user.nationality").type(JsonFieldType.STRING).description("User Nationality"),
                                fieldWithPath("user.profilePic").type(JsonFieldType.STRING).description("User Profile Picture URL"),
                                fieldWithPath("user.createdAt").type(JsonFieldType.STRING).description("User Creation Date"),
                                fieldWithPath("user.updatedAt").type(JsonFieldType.STRING).description("User Last Updated Date"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("text").type(JsonFieldType.STRING).description("Text"),
                                fieldWithPath("mediaList").type(JsonFieldType.ARRAY).description("List of Media"),
                                fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("Number of comments"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Post creation time"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Post last updated time"),
                                fieldWithPath("target").type(JsonFieldType.VARIES).description("target")
                        )
                ));
    }

    @Test
    void getPostListByDate() throws Exception {
        //given
        LocalDate date = LocalDate.now();
        PostResponseDto postA = PostResponseDto.builder()
                .postId(1L)
                .title("title")
                .build();
        PostResponseDto postB = PostResponseDto.builder()
                .postId(2L)
                .title("title")
                .build();
        Page<PostResponseDto> posts = new PageImpl<>(List.of(postA, postB));
        //when
        when(postService.getPostsByDate(date, 0)).thenReturn(posts);
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get(String.format("/post/list?date=%s&page=0", date)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].postId").value(postA.getPostId()))
                .andExpect(jsonPath("$.content[0].title").value(postA.getTitle()));
    }

    @Test
    void getPostListByUser() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .name("test")
                .build();
        PostResponseDto postA = PostResponseDto.builder()
                .postId(1L)
                .user(user.toDto())
                .title("title")
                .build();
        PostResponseDto postB = PostResponseDto.builder()
                .postId(2L)
                .user(user.toDto())
                .title("title")
                .build();
        Page<PostResponseDto> posts = new PageImpl<>(List.of(postA, postB));

        //when
        when(postService.getPostsByUser(1L, 0)).thenReturn(posts);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/post/list?userId=1&page=0"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].postId").value(postA.getPostId()))
                .andExpect(jsonPath("$.content[0].title").value(postA.getTitle()));

    }
}