package com.politicia.coreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.comment.CommentPatchRequestDto;
import com.politicia.coreservice.dto.request.comment.CommentPostRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;

    @Test
    void testCreateComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content("""
                                        {
                                            "userId": "1",
                                            "postId": "1",
                                            "text": "text"
                                        }"""
                                ))
                        .andExpect(status().isCreated());
        Mockito.verify(commentService, times(1)).createComment(any(CommentPostRequestDto.class));

    }

    @Test
    void testEditComment() throws Exception {

        CommentPatchRequestDto responseBody = CommentPatchRequestDto
                .builder()
                .text("newText")
                .build();
        ObjectMapper mapper = new JsonMapper();
        mockMvc.perform(MockMvcRequestBuilders.patch("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(mapper.writeValueAsString(responseBody)))
                .andExpect(status().isOk());
        Mockito.verify(commentService, times(1)).editComment(eq(1L), any(CommentPatchRequestDto.class));

    }

    @Test
    void testDeleteComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/comment/1"))
                .andExpect(status().isOk());
        Mockito.verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    void testGetComments() throws Exception {
        User user = User
                .builder()
                .id(1L)
                .build();
        CommentResponseDto commentA = CommentResponseDto
                .builder()
                .commentId(1L)
                .user(user.toDto())
                .text("text")
                .build();
        CommentResponseDto commentB = CommentResponseDto
                .builder()
                .commentId(2L)
                .user(user.toDto())
                .text("text2")
                .build();

        Page<CommentResponseDto> expectedDto = new PageImpl<>(List.of(commentA, commentB));
        //when
        when(commentService.getCommentListByUser(1L, 0)).thenReturn(expectedDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/comment?userId=1&page=0"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].commentId").value(commentA.getCommentId()))
                .andExpect(jsonPath("$.content[1].commentId").value(commentB.getCommentId()));
    }
}