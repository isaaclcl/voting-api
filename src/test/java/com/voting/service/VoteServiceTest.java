package com.voting.service;

import com.voting.exception.BusinessException;
import com.voting.exception.DuplicatedKeyException;
import com.voting.exception.ResourceNotFoundException;
import com.voting.modal.Agenda;
import com.voting.modal.Session;
import com.voting.modal.Vote;
import com.voting.repository.VoteRepository;
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
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @InjectMocks
    VoteService voteService;
    @Mock
    VoteRepository voteRepository;

    Vote getSampleVote() {
        return new Vote("voteId", getSampleSession(), "CPF", "SIM");
    }

    Vote getSampleVoteWithoutSession() {
        return new Vote("voteId", null, "CPF", "SIM");
    }

    private Session getSampleSession() {
        Session session = new Session("sessionId", getSampleAgenda());
        session.setExpireDate(ZonedDateTime.now().plusDays(1L));
        return session;
    }

    private Session getSampleExpiredSession() {
        Session session = new Session("sessionId", getSampleAgenda());
        session.setExpireDate(ZonedDateTime.now().minusDays(1L));
        return session;
    }

    private Agenda getSampleAgenda() {
        return new Agenda("agendaId");
    }

    @Test
    void whenListAllThenReturnAll() {
        Vote sampleVote = getSampleVote();
        when(this.voteRepository.findAll()).thenReturn(List.of(sampleVote));

        List<Vote> all = this.voteService.findAll();

        assertNotNull(all);
        assertIterableEquals(List.of(sampleVote), all);
    }

    @Test
    void givenVoteWithoutSession_whenSave_ThenThrowResourceNotFoundException() {
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> this.voteService.newVote(getSampleVoteWithoutSession())
        );
        assertTrue(thrown.getMessage().contains("Not Found"));

    }

    @Test
    void givenVoteWithExpiredSession_whenSave_ThenThrowBusinessException() {

        Session sessionExpired = getSampleExpiredSession();
        Vote sampleVote = getSampleVote();
        sampleVote.setSession(sessionExpired);

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> this.voteService.newVote(sampleVote)
        );
        assertTrue(thrown.getMessage().contains("Expired"));

    }

    @Test
    void givenVoteAgain_whenSave_ThenThrowBusinessException() {
        Vote sampleVote = getSampleVote();
        when(this.voteRepository.findVoteBySession_Agenda_IdAndCPF(sampleVote.getSession().getAgenda().getId(), sampleVote.getCPF())).thenReturn(sampleVote);

        DuplicatedKeyException thrown = assertThrows(
                DuplicatedKeyException.class,
                () -> this.voteService.newVote(sampleVote)
        );
        assertTrue(thrown.getMessage().contains("Duplicated"));

    }

    @Test
    void givenValidVote_whenSave_ThenReturn202() {
        Vote sampleVote = getSampleVote();

        when(this.voteRepository.save(sampleVote)).thenReturn(sampleVote);

        Vote vote = voteService.newVote(sampleVote);

        assertEquals(sampleVote, vote);

    }

}