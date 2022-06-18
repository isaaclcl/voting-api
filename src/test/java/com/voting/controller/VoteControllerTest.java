package com.voting.controller;

import com.voting.service.VoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = VoteController.class)
class VoteControllerTest {

    @MockBean
    VoteService voteService;

    @Resource
    private MockMvc mvc;

    @Test
    void givenInputIsValid_whenPost_thenReturnsStatus201() throws Exception {
        String body = validSession();

        mvc.perform(post("/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    private String validSession() {
        return "{\n" +
                "    \"session\": {\n" +
                "        \"id\": \"62a94cf1c4ae786119d2ae27\",\n" +
                "        \"expireDate\": \"2022-06-15T21:35:08.368-03:00\",\n" +
                "        \"agenda\": \"62a946a30aabce5141bce640\"\n" +
                "    },\n" +
                "    \"value\": \"SIM\",\n" +
                "    \"CPF\": \"123456789\"\n" +
                "}";
    }

}