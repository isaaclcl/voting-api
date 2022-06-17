package com.voting.service;

import com.voting.modal.Agenda;
import com.voting.repository.AgendaRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public Agenda newAgenda() {
        return this.agendaRepository.save(new Agenda());
    }

    public List<Agenda> findAll() {
        return this.agendaRepository.findAll();
    }

}
