package com.politicia.coreservice.controller;

import com.politicia.coreservice.service.MediaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MediaController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class MediaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MediaService mediaService;

    @Test
    void testCreateMedia() {
    }

    @Test
    void testDeleteMedia() {
    }

    @Test
    void testGetMedia() {
    }
}