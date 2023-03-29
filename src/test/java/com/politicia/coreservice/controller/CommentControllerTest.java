package com.politicia.coreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.politicia.coreservice.domain.Comment;
import com.politicia.coreservice.domain.Post;
import com.politicia.coreservice.domain.User;
import com.politicia.coreservice.domain.like.CommentLike;
import com.politicia.coreservice.dto.request.comment.CommentPatchRequestDto;
import com.politicia.coreservice.dto.request.comment.CommentPostRequestDto;
import com.politicia.coreservice.dto.response.CommentResponseDto;
import com.politicia.coreservice.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
    void testGetCommentsByUser() throws Exception {
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
        Comment commentA = Comment
                .builder()
                .id(1L)
                .user(user)
                .post(post)
                .text("text")
                .build();
        Comment commentB = Comment
                .builder()
                .id(2L)
                .user(user)
                .post(post)
                .text("text2")
                .build();
        CommentLike commentLike1 = CommentLike.builder()
                .commentLikeId(1L)
                .user(user)
                .comment(commentA)
                .build();
        commentA.getLikes().add(commentLike1);
        Page<CommentResponseDto> expectedDto = new PageImpl<>(List.of(commentA.toDto(), commentB.toDto()));
        //when
        when(commentService.getCommentListByUser(1L, 0)).thenReturn(expectedDto);
        mockMvc.perform(RestDocumentationRequestBuilders.get("/comment/user/{userId}?page={page}", 1L, 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].commentId").value(commentA.toDto().getCommentId()))
                .andExpect(jsonPath("$.content[1].commentId").value(commentB.toDto().getCommentId()))
                .andDo(
                        document("comment-get-by-user",
                                pathParameters(
                                        parameterWithName("userId").description("User ID")
                                ),
                                queryParameters(
                                        parameterWithName("page").description("Page number")
                                ),
                                responseFields(
                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("List of Comments"),
                                        fieldWithPath("content[].commentId").type(JsonFieldType.NUMBER).description("Comment ID"),
                                        fieldWithPath("content[].user").type(JsonFieldType.OBJECT).description("User info of Comment author"),
                                        fieldWithPath("content[].user.id").type(JsonFieldType.NUMBER).description("User ID"),
                                        fieldWithPath("content[].user.name").type(JsonFieldType.STRING).description("Username"),
                                        fieldWithPath("content[].user.nationality").type(JsonFieldType.STRING).description("User's Nationality"),
                                        fieldWithPath("content[].user.profilePic").type(JsonFieldType.STRING).description("User's Profile image"),
                                        fieldWithPath("content[].user.createdAt").type(JsonFieldType.STRING).description("User Creation Date"),
                                        fieldWithPath("content[].user.updatedAt").type(JsonFieldType.STRING).description("User Last Updated Date"),
                                        fieldWithPath("content[].postId").type(JsonFieldType.NUMBER).description("Post ID of Comment"),
                                        fieldWithPath("content[].text").type(JsonFieldType.STRING).description("Comment Text Content"),
                                        fieldWithPath("content[].likes").type(JsonFieldType.ARRAY).description("Comment Likes"),
                                        fieldWithPath("content[].likes[].likeId").description(JsonFieldType.NUMBER).description("Like record ID"),
                                        fieldWithPath("content[].likes[].userId").description(JsonFieldType.NUMBER).description("User who liked"),
                                        fieldWithPath("content[].likes[].createdAt").description(JsonFieldType.NUMBER).description("Like Creation Date"),
                                        fieldWithPath("content[].likes[].updatedAt").description(JsonFieldType.NUMBER).description("Like Last Updated Date"),
                                        fieldWithPath("content[].likeCount").type(JsonFieldType.NUMBER).description("Number of Comment Likes"),
                                        fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("Comment Creation Date"),
                                        fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("Comment Last Updated Date"),
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
                        )
                );
    }

    @Test
    void testGetCommentsByPost() throws Exception {
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
        Comment commentA = Comment
                .builder()
                .id(1L)
                .user(user)
                .post(post)
                .text("text")
                .build();
        Comment commentB = Comment
                .builder()
                .id(2L)
                .user(user)
                .post(post)
                .text("text2")
                .build();
        CommentLike commentLike1 = CommentLike.builder()
                .commentLikeId(1L)
                .user(user)
                .comment(commentA)
                .build();
        commentA.getLikes().add(commentLike1);

        Page<CommentResponseDto> expectedDto = new PageImpl<>(List.of(commentA.toDto(), commentB.toDto()));
        //when
        when(commentService.getCommentListByPost(1L, 0)).thenReturn(expectedDto);
        mockMvc.perform(RestDocumentationRequestBuilders.get("/comment/post/{postId}?page={page}", 1L, 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].commentId").value(commentA.toDto().getCommentId()))
                .andExpect(jsonPath("$.content[1].commentId").value(commentB.toDto().getCommentId()))
                .andDo(
                        document("comment-get-by-post",
                                pathParameters(
                                        parameterWithName("postId").description("Post ID")
                                ),
                                queryParameters(
                                        parameterWithName("page").description("Page number")
                                ),
                                responseFields(
                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("List of Comments"),
                                        fieldWithPath("content[].commentId").type(JsonFieldType.NUMBER).description("Comment ID"),
                                        fieldWithPath("content[].user").type(JsonFieldType.OBJECT).description("User info of Comment author"),
                                        fieldWithPath("content[].user.id").type(JsonFieldType.NUMBER).description("User ID"),
                                        fieldWithPath("content[].user.name").type(JsonFieldType.STRING).description("Username"),
                                        fieldWithPath("content[].user.nationality").type(JsonFieldType.STRING).description("User's Nationality"),
                                        fieldWithPath("content[].user.profilePic").type(JsonFieldType.STRING).description("User's Profile image"),
                                        fieldWithPath("content[].user.createdAt").type(JsonFieldType.STRING).description("User Creation Date"),
                                        fieldWithPath("content[].user.updatedAt").type(JsonFieldType.STRING).description("User Last Updated Date"),
                                        fieldWithPath("content[].postId").type(JsonFieldType.NUMBER).description("Post ID of Comment"),
                                        fieldWithPath("content[].text").type(JsonFieldType.STRING).description("Comment Text Content"),
                                        fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("Comment Creation Date"),
                                        fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("Comment Last Updated Date"),
                                        fieldWithPath("content[].likes").type(JsonFieldType.ARRAY).description("Comment Likes"),
                                        fieldWithPath("content[].likes[].likeId").description(JsonFieldType.NUMBER).description("Like record ID"),
                                        fieldWithPath("content[].likes[].userId").description(JsonFieldType.NUMBER).description("User who liked"),
                                        fieldWithPath("content[].likes[].createdAt").description(JsonFieldType.NUMBER).description("Like Creation Date"),
                                        fieldWithPath("content[].likes[].updatedAt").description(JsonFieldType.NUMBER).description("Like Last Updated Date"),
                                        fieldWithPath("content[].likeCount").type(JsonFieldType.NUMBER).description("Number of Comment Likes"),
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
                        )
                );
    }
}