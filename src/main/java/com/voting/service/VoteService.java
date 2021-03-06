package com.voting.service;

import com.voting.common.webClient.cpf.CPFClient;
import com.voting.exception.BusinessException;
import com.voting.exception.DuplicatedKeyException;
import com.voting.exception.ResourceNotFoundException;
import com.voting.modal.dto.AgendaVoteCountDTO;
import com.voting.modal.dto.VoteCountDTO;
import com.voting.modal.dto.VoteDTO;
import com.voting.modal.tables.ElectionSession;
import com.voting.modal.tables.Vote;
import com.voting.repository.AgendaRepository;
import com.voting.repository.SessionRepository;
import com.voting.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.voting.constant.ApiConstants.ERROR_CPF_UNABLE_TO_VOTE;
import static com.voting.constant.ApiConstants.ERROR_DUPLICATED_VOTE;
import static com.voting.constant.ApiConstants.ERROR_SESSION_EXPIRED;
import static com.voting.constant.ApiConstants.ERROR_SESSION_NOT_FOUND;
import static com.voting.constant.ApiConstants.NAO;
import static com.voting.constant.ApiConstants.SIM;
import static com.voting.modal.mapper.EntityMapper.ENTITY_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;

    private final AgendaRepository agendaRepository;

    private final SessionRepository sessionRepository;

    private final CPFClient cpfClient;

    public VoteDTO newVote(VoteDTO voteDto) {
        Vote vote = ENTITY_MAPPER.map(voteDto);
        Optional<ElectionSession> electionSession = this.sessionRepository.findById(voteDto.getElectionSession());
        if (electionSession.isPresent()) {
            vote.setElectionSession(electionSession.get());
        }else {
            throw new ResourceNotFoundException(ERROR_SESSION_NOT_FOUND);
        }

        if (ZonedDateTime.now().isAfter(vote.getElectionSession().getExpireDate())) {
            throw new BusinessException(ERROR_SESSION_EXPIRED);
        }

        if (!this.cpfClient.isAbleToVote(vote.getCpf())) {
            throw new BusinessException(ERROR_CPF_UNABLE_TO_VOTE);
        }

        Vote voteBySession_agenda_idAndCPF = this.voteRepository.findVoteByElectionSession_Agenda_IdAndCpf(vote.getElectionSession().getAgenda().getId(), vote.getCpf());
        if (voteBySession_agenda_idAndCPF != null) {
            throw new DuplicatedKeyException(ERROR_DUPLICATED_VOTE);
        }

        else {
            Vote saved = this.voteRepository.save(vote);
            return ENTITY_MAPPER.map(saved);
        }
    }

    public List<Vote> findAll() {
        return this.voteRepository.findAll();
    }

    public AgendaVoteCountDTO countByAgenda(Long agendaID) {
        if (this.agendaRepository.findById(agendaID).isEmpty()){
            throw new ResourceNotFoundException("Agenda Not Found");
        }
        List<VoteCountDTO> voteCounts = new ArrayList<>();
        Long yes = this.voteRepository.countVoteByElectionSession_Agenda_IdAndChoose(agendaID, SIM);
        Long no = this.voteRepository.countVoteByElectionSession_Agenda_IdAndChoose(agendaID, NAO);
        voteCounts.add(new VoteCountDTO(SIM, yes));
        voteCounts.add(new VoteCountDTO(NAO, no));
        return AgendaVoteCountDTO.builder().agendaId(agendaID).votes(voteCounts).build();
    }


}
