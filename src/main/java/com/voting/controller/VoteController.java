package com.voting.controller;

import com.voting.modal.dto.VoteCountDTO;
import com.voting.modal.dto.VoteDTO;
import com.voting.service.VoteService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.voting.constant.ApiConstants.COUNT_VOTE_BY_AGENDA_ID;
import static com.voting.constant.ApiConstants.ENDPOINT_VOTES;

@RestController
@RequestMapping(ENDPOINT_VOTES)
@Getter
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDTO newVote(@RequestBody VoteDTO voteDTO) {
        return this.voteService.newVote(voteDTO);
    }

    @GetMapping(COUNT_VOTE_BY_AGENDA_ID)
    public List<VoteCountDTO> countByAgenda(@NotBlank @PathVariable Long agendaId) {
        return this.voteService.countByAgenda(agendaId);
    }

}
