package com.politicia.coreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.dto.request.comment.CommentPatchRequestDto;
import com.politicia.coreservice.dto.request.comment.CommentPostRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;


    @Test
    void testCreateComment() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post("/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content("""
                                        {
                                            "userId": 1,
                                            "postId": 1,
                                            "text": "text"
                                        }"""
                                ))
                        .andExpect(status().isCreated())
                .andDo(document("comment-post",
                            requestFields(
                                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User ID"),
                                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("Post ID"),
                                    fieldWithPath("text").type(JsonFieldType.STRING).description("Text")
                            )));
        Mockito.verify(commentService, times(1)).createComment(any(CommentPostRequestDto.class));

    }

    @Test
    void testEditComment() throws Exception {

        CommentPatchRequestDto responseBody = CommentPatchRequestDto
                .builder()
                .text("newText")
                .build();
        ObjectMapper mapper = new JsonMapper();
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(mapper.writeValueAsString(responseBody)))
                .andExpect(status().isOk())
                .andDo(document("comment-patch",
                        requestFields(
                                fieldWithPath("text").type(JsonFieldType.STRING).description("text")
                        )));
        Mockito.verify(commentService, times(1)).editComment(eq(1L), any(CommentPatchRequestDto.class));

    }

    @Test
    void testDeleteComment() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/comment/{commentId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("comment-delete",
                        pathParameters(parameterWithName("commentId").description("Comment ID"))));
        Mockito.verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    void testGetComments() throws Exception {
        User user = User
                .builder()
                .id(1L)
                .name("username")
                .nationality("test")
                .profilePic("https://profile.pic")
                .build();
        Post post = Post
                .builder()
                .id(1L)
                .title("title")
                .text("text")
                .build();
        CommentResponseDto commentA = Comment
                .builder()
                .id(1L)
                .user(user)
                .post(post)
                .text("text")
                .build()
                .toDto();
        CommentResponseDto commentB = Comment
                .builder()
                .id(2L)
                .user(user)
                .post(post)
                .text("text2")
                .build()
                .toDto();

        Page<CommentResponseDto> expectedDto = new PageImpl<>(List.of(commentA, commentB));
        //when
        when(commentService.getCommentListByUser(1L, 0)).thenReturn(expectedDto);
        mockMvc.perform(RestDocumentationRequestBuilders.get("/comment?userId={userId}&page={page}", 1L, 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].commentId").value(commentA.getCommentId()))
                .andExpect(jsonPath("$.content[1].commentId").value(commentB.getCommentId()))
                .andDo(
                        document("comment-get",
                                queryParameters(
                                        parameterWithName("userId").description("User ID"),
                                        parameterWithName("page").description("Page number")
                                ),
                                responseFields(
                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("List of Comments"),
                                        fieldWithPath("content[].commentId").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("content[].user").type(JsonFieldType.OBJECT).description(""),
                                        fieldWithPath("content[].user.id").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("content[].user.name").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("content[].user.nationality").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("content[].user.profilePic").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("content[].user.createdAt").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("content[].user.updatedAt").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("content[].postId").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("content[].text").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("pageable").type(JsonFieldType.STRING).description(""),
                                        fieldWithPath("last").type(JsonFieldType.BOOLEAN).description(""),
                                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("first").type(JsonFieldType.BOOLEAN).description(""),
                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("number").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("sort").type(JsonFieldType.OBJECT).description(""),
                                        fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description(""),
                                        fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
                                        fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
                                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description(""),
                                        fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("")
                                )
                        )
                );
    }
}