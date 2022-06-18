package com.voting.service;

import com.voting.modal.dto.AgendaDTO;
import com.voting.modal.mapper.EntityMapper;
import com.voting.modal.tables.Agenda;
import com.voting.repository.AgendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @InjectMocks
    AgendaService agendaService;

    @Mock
    AgendaRepository agendaRepository;

    Agenda getSample() {
        return new Agenda(1L);
    }

    AgendaDTO getSampleDTO(){
        return EntityMapper.ENTITY_MAPPER.map(this.getSample());
    }

    @Test
    void whenListAllThenReturn200() {
        when(this.agendaRepository.findAll()).thenReturn(List.of(getSample()));

        List<AgendaDTO> all = this.agendaService.findAll();

        assertNotNull(all);
        assertEquals(List.of(getSampleDTO()), all);
    }

    @Test
    void whenSaveThenReturn200() {
        when(this.agendaRepository.save(any(Agenda.class))).thenReturn(getSample());

        AgendaDTO saved = this.agendaService.newAgenda();

        assertNotNull(saved);
        assertEquals(getSampleDTO(), saved);
    }

}