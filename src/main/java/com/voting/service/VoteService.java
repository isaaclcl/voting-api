package com.voting.service;

import com.voting.exception.BusinessException;
import com.voting.exception.DuplicatedKeyException;
import com.voting.exception.ResourceNotFoundException;
import com.voting.modal.Vote;
import com.voting.modal.projection.VoteCount;
import com.voting.repository.SessionRepository;
import com.voting.repository.VoteRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.voting.constant.ApiConstants.ERROR_DUPLICATED_VOTE;
import static com.voting.constant.ApiConstants.ERROR_SESSION_EXPIRED;
import static com.voting.constant.ApiConstants.ERROR_SESSION_NOT_FOUND;
import static com.voting.constant.ApiConstants.KAFKA_VOTE_TOPIC;
import static com.voting.constant.ApiConstants.NAO;
import static com.voting.constant.ApiConstants.SIM;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class VoteService {

    private KafkaTemplate<String, String> kafkaTemplate;

    private final VoteRepository voteRepository;

    private final SessionRepository sessionRepository;

    public Vote newVote(Vote vote) {
        if (isNull(vote.getSession())) {
            throw new ResourceNotFoundException(ERROR_SESSION_NOT_FOUND);
        }
        if (ZonedDateTime.now().isAfter(vote.getSession().getExpireDate())) {
            throw new BusinessException(ERROR_SESSION_EXPIRED);
        }

        Vote voteBySession_agenda_idAndCPF = this.voteRepository.findVoteBySession_Agenda_IdAndCPF(vote.getSession().getAgenda().getId(), vote.getCPF());
        if (voteBySession_agenda_idAndCPF == null) {
            return this.voteRepository.save(vote);
        } else {
            throw new DuplicatedKeyException(ERROR_DUPLICATED_VOTE);
        }
    }

    public List<Vote> findAll() {
        return this.voteRepository.findAll();
    }

    public List<VoteCount> countByAgenda(String agendaID) {
        List<VoteCount> voteCounts = new ArrayList<>();
        Long yes = this.voteRepository.countVoteBySession_Agenda_IdAndValue(agendaID, SIM);
        Long no = this.voteRepository.countVoteBySession_Agenda_IdAndValue(agendaID, NAO);
        voteCounts.add(new VoteCount(SIM, yes));
        voteCounts.add(new VoteCount(NAO, no));
        return voteCounts;
    }

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(KAFKA_VOTE_TOPIC, message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.debug(String.format("Sent message=[%s] with offset=[%s]",
                        message, result.getRecordMetadata().offset()));
            }

            @Override
            public void onFailure(@NonNull Throwable ex) {
                log.error(String.format("Unable to send message=[%s] due to : %s", message, ex.getMessage()));
            }
        });
    }

}
