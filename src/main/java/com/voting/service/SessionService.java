package com.voting.service;

import com.voting.exception.ResourceNotFoundException;
import com.voting.messager.VoteProducer;
import com.voting.modal.dto.ElectionSessionDTO;
import com.voting.modal.tables.Agenda;
import com.voting.modal.tables.ElectionSession;
import com.voting.repository.AgendaRepository;
import com.voting.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.voting.modal.mapper.EntityMapper.ENTITY_MAPPER;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final AgendaRepository agendaRepository;

    private final VoteProducer voteProducer;

    public ElectionSessionDTO newSession(ElectionSessionDTO electionSessionDTO) {
        ElectionSession electionSession = ENTITY_MAPPER.map(electionSessionDTO);
        Optional<Agenda> agenda = this.agendaRepository.findById(electionSessionDTO.getAgenda());
        if (agenda.isPresent()){
            electionSession.setAgenda(agenda.get());
        } else {
            throw new ResourceNotFoundException("Agenda Not Found");
        }
        ElectionSession saved = this.sessionRepository.save(electionSession);
        return ENTITY_MAPPER.map(saved);
    }

    public List<ElectionSessionDTO> findAll() {
        return this.sessionRepository.findAll().stream().map(ENTITY_MAPPER::map).collect(Collectors.toList());
    }

}
