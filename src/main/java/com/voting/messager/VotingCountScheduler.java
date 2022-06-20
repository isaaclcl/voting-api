package com.voting.messager;

import com.voting.modal.dto.AgendaVoteCountDTO;
import com.voting.modal.tables.ElectionSession;
import com.voting.repository.SessionRepository;
import com.voting.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@Slf4j
@RequiredArgsConstructor
public class VotingCountScheduler {

    private final SessionRepository sessionRepository;

    private final VoteService voteService;

    private final VoteProducer voteProducer;

    @Scheduled(fixedDelay = 10000)
    public void notifyVotingCount() {
        List<ElectionSession> sessions = this.sessionRepository.findElectionSessionsByResultSentFalseAndExpireDateBefore(ZonedDateTime.now());
        if (!isEmpty(sessions)) {
            sessions.forEach(electionSession -> {
                AgendaVoteCountDTO agendaVoteCountDTO = this.voteService.countByAgenda(electionSession.getAgenda().getId());
                voteProducer.sendMessage(agendaVoteCountDTO);
                electionSession.setResultSent(true);
                this.sessionRepository.save(electionSession);
            });
        }
    }


}
