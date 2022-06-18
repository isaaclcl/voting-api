package com.voting.service;

import com.voting.modal.dto.AgendaDTO;
import com.voting.modal.tables.Agenda;
import com.voting.repository.AgendaRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.voting.modal.mapper.EntityMapper.ENTITY_MAPPER;

@Service
@Data
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaDTO newAgenda() {
        return ENTITY_MAPPER.map(this.agendaRepository.save(new Agenda()));
    }

    public List<AgendaDTO> findAll() {
        return this.agendaRepository.findAll().stream().map(ENTITY_MAPPER::map).collect(Collectors.toList());
    }

}
