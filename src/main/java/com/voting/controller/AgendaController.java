package com.voting.controller;

import com.voting.modal.dto.AgendaDTO;
import com.voting.modal.dto.VoteCountDTO;
import com.voting.service.AgendaService;
import com.voting.service.VoteService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.voting.constant.ApiConstants.COUNT_VOTE_BY_AGENDA_ID;
import static com.voting.constant.ApiConstants.ENDPOINT_AGENDA;

@RestController
@RequestMapping(ENDPOINT_AGENDA)
@Getter
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    private final VoteService voteService;

    @GetMapping
    public List<AgendaDTO> listAll() {
        return this.agendaService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendaDTO newAgenda() {
        return this.agendaService.newAgenda();
    }

    @GetMapping(COUNT_VOTE_BY_AGENDA_ID)
    public List<VoteCountDTO> countVotes(@PathVariable Long agendaId) {
        return this.voteService.countByAgenda(agendaId);
    }

}