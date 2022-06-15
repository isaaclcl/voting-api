package com.voting.service;

import com.voting.exception.BusinessException;
import com.voting.exception.DuplicatedKeyException;
import com.voting.exception.ResourceNotFoundException;
import com.voting.modal.Vote;
import com.voting.modal.projection.VoteCount;
import com.voting.repository.SessionRepository;
import com.voting.repository.VoteRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class VoteService {

    private final VoteRepository voteRepository;

    private final SessionRepository sessionRepository;

    public Vote newVote(Vote vote) {
        if (isNull(vote.getSession())) {
            throw new ResourceNotFoundException("Session Not Found");
        }
        if (ZonedDateTime.now().isAfter(vote.getSession().getExpireDate())) {
            throw new BusinessException("Session Expired");
        }

        Vote voteBySession_agenda_idAndCPF = this.voteRepository.findVoteBySession_Agenda_IdAndCPF(vote.getSession().getAgenda().getId(), vote.getCPF());
        if (voteBySession_agenda_idAndCPF == null) {
            return this.voteRepository.save(vote);
        } else {
            throw new DuplicatedKeyException("Duplicated Vote");
        }
    }

    public List<Vote> findAll() {
        return this.voteRepository.findAll();
    }

    public List<VoteCount> countByAgenda(String agendaID) {
        List<VoteCount> voteCounts = new ArrayList<>();
        Long sim = this.voteRepository.countVoteBySession_Agenda_IdAndValue(agendaID, "SIM");
        Long nao = this.voteRepository.countVoteBySession_Agenda_IdAndValue(agendaID, "NAO");
        voteCounts.add(new VoteCount("SIM", sim));
        voteCounts.add(new VoteCount("NAO", nao));
        return voteCounts;
    }
}
