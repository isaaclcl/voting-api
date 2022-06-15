package com.voting.service;

import com.voting.modal.Agenda;
import com.voting.modal.Session;
import com.voting.repository.SessionRepository;
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
class SessionServiceTest {

    @InjectMocks
    SessionService sessionService;

    @Mock
    SessionRepository sessionRepository;

    Session getSample() {
        return new Session("agendaId", new Agenda("agendaId"));
    }

    @Test
    void whenListAllThenReturn200() {
        when(this.sessionRepository.findAll()).thenReturn(List.of(getSample()));

        List<Session> all = this.sessionService.findAll();

        assertNotNull(all);
        assertEquals(List.of(getSample()), all);
    }

    @Test
    void whenSaveThenReturn200() {
        when(this.sessionRepository.save(any(Session.class))).thenReturn(getSample());

        Session saved = this.sessionService.newSession(getSample());

        assertNotNull(saved);
        assertEquals(getSample(), saved);
    }

}