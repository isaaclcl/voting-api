package com.voting.controller;

import com.voting.service.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SessionController.class)
class ElectionSessionControllerTest {

    @MockBean
    SessionService sessionService;

    @Resource
    private MockMvc mvc;

    @Test
    void whenInputIsInvalid_thenReturnsStatus400() throws Exception {
        String body = invalidSession();

        mvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInputIsValid_thenReturnsStatus200() throws Exception {
        String body = validSession();

        mvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void whenListAll_thenReturnsStatus200() throws Exception {

        mvc.perform(get("/sessions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String validSession() {
        return "{\"agenda\":  \"1\"}";

    }

    private String invalidSession() {
        return "{}";
    }

}