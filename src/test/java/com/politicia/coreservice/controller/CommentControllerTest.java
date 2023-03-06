package com.politicia.coreservice.controller;

import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.CommentRequestDto;
import com.politicia.coreservice.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                                .content("{\n" +
                                        "    \"userId\": \"1\",\n" +
                                        "    \"postId\": \"1\",\n" +
                                        "    \"text\": \"text\"\n" +
                                        "}"
                                ))
                        .andExpect(status().isCreated());
        Mockito.verify(commentService, times(1)).createComment(any(CommentRequestDto.class));

    }

    @Test
    void testEditComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("""
                                {
                                    "userId": "1",
                                    "postId": "1",
                                    "text": "newText"
                                }"""
                        ))
                .andExpect(status().isOk());
        Mockito.verify(commentService, times(1)).editComment(1L, any(CommentRequestDto.class));

    }

    @Test
    void testDeleteComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/1"))
                .andExpect(status().isOk());
        Mockito.verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    void testGetComments() {
        
    }
}