package com.voting.service;

import com.voting.exception.ResourceNotFoundException;
import com.voting.modal.dto.ElectionSessionDTO;
import com.voting.modal.tables.Agenda;
import com.voting.modal.tables.ElectionSession;
import com.voting.repository.AgendaRepository;
import com.voting.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.voting.modal.mapper.EntityMapper.ENTITY_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @InjectMocks
    SessionService sessionService;

    @Mock
    SessionRepository sessionRepository;

    @Mock
    AgendaRepository agendaRepository;

    ElectionSession getSample() {
        return ElectionSession.builder().id(1L).agenda(new Agenda(1L)).expireDate(ZonedDateTime.now()).build();
    }

    @Test
    void whenListAllThenReturn200() {
        ElectionSession sample = getSample();
        when(this.sessionRepository.findAll()).thenReturn(List.of(sample));

        List<ElectionSessionDTO> all = this.sessionService.findAll();

        assertNotNull(all);
        assertEquals(ENTITY_MAPPER.map(sample), all.get(0));
    }

    @Test
    void whenSaveThenReturn200() {
        ElectionSession sample = getSample();
        ElectionSessionDTO sessionDTO = ENTITY_MAPPER.map(sample);
        when(this.agendaRepository.findById(anyLong())).thenReturn(Optional.of(sample.getAgenda()));
        when(this.sessionRepository.save(any(ElectionSession.class))).thenReturn(sample);

        ElectionSessionDTO saved = this.sessionService.newSession(sessionDTO);

        assertNotNull(saved);
        assertEquals(sessionDTO, saved);
    }

    @Test
    void givenInValidAgendaIdWhenCountByAgendaThenThrowBusinessException() {
        when(this.agendaRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> this.sessionService.newSession(ENTITY_MAPPER.map(getSample()))
        );
        assertTrue(thrown.getMessage().contains("Not Found"));
    }


}