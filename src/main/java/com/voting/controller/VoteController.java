package com.voting.controller;

import com.voting.modal.Vote;
import com.voting.modal.projection.VoteCount;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.voting.constant.ApiConstants.ENDPOINT_VOTES;
import static com.voting.constant.ApiConstants.ENDPOINT_VOTES_PATH_COUNT_AGENDA_ID;

@RestController
@RequestMapping(ENDPOINT_VOTES)
@Getter
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @GetMapping
    public List<Vote> listAll() {
        return this.voteService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vote newVote(@Valid @RequestBody Vote vote) {
        return this.voteService.newVote(vote);
    }

    @GetMapping(ENDPOINT_VOTES_PATH_COUNT_AGENDA_ID)
    public List<VoteCount> countByAgenda(@NotBlank @PathVariable Long agendaId) {
        return this.voteService.countByAgenda(agendaId);
    }

}
