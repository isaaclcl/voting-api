package com.voting.controller;

import com.voting.service.AgendaService;
import com.voting.service.VoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static com.voting.constant.ApiConstants.ENDPOINT_AGENDA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AgendaController.class)
class AgendaControllerTest {

    @MockBean
    AgendaService agendaService;

    @MockBean
    VoteService voteService;

    @Resource
    private MockMvc mvc;

    @Test
    void whenListAll_thenReturnsStatus200() throws Exception {

        mvc.perform(get(ENDPOINT_AGENDA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenCreate_thenReturnsStatus201() throws Exception {

        mvc.perform(post(ENDPOINT_AGENDA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}