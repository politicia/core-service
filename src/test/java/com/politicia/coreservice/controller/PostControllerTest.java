package com.politicia.coreservice.controller;

import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.post.PostPatchRequestDto;
import com.politicia.coreservice.dto.request.post.PostPostRequestDto;
import com.politicia.coreservice.dto.response.PostResponseDto;
import com.politicia.coreservice.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PostController.class)
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
        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "    \"userId\": \"1\",\n" +
                                "    \"title\": \"text\",\n" +
                                "    \"text\": \"text\"\n" +
                                "}"
                        ))
                .andExpect(status().isCreated());
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
        mockMvc.perform(patch("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\n" +
                        "    \"userId\": \"1\",\n" +
                        "    \"title\": \"titleEdited\",\n" +
                        "    \"text\": \"textEdited\"\n" +
                        "}"
                ))
                .andExpect(status().isOk());
        verify(postService, times(1)).editPost(any(Long.class), any(PostPatchRequestDto.class));

    }

    @Test
    void deletePost() throws Exception {
        //given
        Long postId = 1L;
        //when

        //then
        mockMvc.perform(delete("/post/1"))
                .andExpect(status().isOk());
        verify(postService, times(1)).deletePost(postId);
    }

    @Test
    void getPost() throws Exception {
        //given
        PostResponseDto expectedDto = PostResponseDto.builder()
                .postId(1L)
                .title("title")
                .build();
        //when
        when(postService.getPostById(1L)).thenReturn(expectedDto);
        //then
        mockMvc.perform(get("/post/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(expectedDto.getPostId()))
                .andExpect(jsonPath("$.title").value(expectedDto.getTitle()));
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
        mockMvc.perform(get(String.format("/post/list?date=%s&page=0", date)))
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
        mockMvc.perform(get("/post/list?userId=1&page=0"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].postId").value(postA.getPostId()))
                .andExpect(jsonPath("$.content[0].title").value(postA.getTitle()));

    }
}