package com.voting.service;

import com.voting.modal.Agenda;
import com.voting.modal.ElectionSession;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ElectionSessionServiceTest {

    @InjectMocks
    SessionService sessionService;

    @Mock
    SessionRepository sessionRepository;

    ElectionSession getSample() {
        return new ElectionSession(1L, new Agenda(1L), ZonedDateTime.now());
    }

    @Test
    void whenListAllThenReturn200() {
        when(this.sessionRepository.findAll()).thenReturn(List.of(getSample()));

        List<ElectionSession> all = this.sessionService.findAll();

        assertNotNull(all);
        assertEquals(List.of(getSample()), all);
    }

    @Test
    void whenSaveThenReturn200() {
        when(this.sessionRepository.save(any(ElectionSession.class))).thenReturn(getSample());

        ElectionSession saved = this.sessionService.newSession(getSample());

        assertNotNull(saved);
        assertEquals(getSample(), saved);
    }

}