package com.voting.service;

import com.voting.exception.BusinessException;
import com.voting.exception.DuplicatedKeyException;
import com.voting.exception.ResourceNotFoundException;
import com.voting.modal.dto.VoteDTO;
import com.voting.modal.tables.Agenda;
import com.voting.modal.tables.ElectionSession;
import com.voting.modal.tables.Vote;
import com.voting.repository.SessionRepository;
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
import java.util.Optional;

import static com.voting.modal.mapper.EntityMapper.ENTITY_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @InjectMocks
    VoteService voteService;
    @Mock
    VoteRepository voteRepository;

    @Mock
    SessionRepository sessionRepository;

    Vote getSampleVote() {
        return new Vote(1L, getSampleSession(), "CPF", "SIM");
    }

    Vote getSampleVoteWithoutSession() {
        return new Vote(1L, null, "CPF", "SIM");
    }

    private ElectionSession getSampleSession() {
        return new ElectionSession(1L, getSampleAgenda(), ZonedDateTime.now().plusDays(1L));
    }

    private ElectionSession getSampleExpiredSession() {
        return new ElectionSession(1L, getSampleAgenda(),ZonedDateTime.now().minusDays(1L));
    }

    private Agenda getSampleAgenda() {
        return new Agenda(1L);
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
                () -> this.voteService.newVote(ENTITY_MAPPER.map(getSampleVoteWithoutSession()))
        );
        assertTrue(thrown.getMessage().contains("Not Found"));

    }

    @Test
    void givenVoteWithExpiredSession_whenSave_ThenThrowBusinessException() {


        ElectionSession electionSessionExpired = getSampleExpiredSession();
        Vote sampleVote = getSampleVote();
        sampleVote.setElectionSession(electionSessionExpired);

        when(this.sessionRepository.findById(anyLong())).thenReturn(Optional.of(electionSessionExpired));

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> this.voteService.newVote(ENTITY_MAPPER.map(sampleVote))
        );
        assertTrue(thrown.getMessage().contains("Expired"));

    }

    @Test
    void givenVoteAgain_whenSave_ThenThrowBusinessException() {
        Vote sampleVote = getSampleVote();
        when(this.sessionRepository.findById(anyLong())).thenReturn(Optional.of(getSampleSession()));
        when(this.voteRepository.findVoteByElectionSession_Agenda_IdAndCpf(sampleVote.getElectionSession().getAgenda().getId(), sampleVote.getCpf())).thenReturn(sampleVote);

        DuplicatedKeyException thrown = assertThrows(
                DuplicatedKeyException.class,
                () -> this.voteService.newVote(ENTITY_MAPPER.map(sampleVote))
        );
        assertTrue(thrown.getMessage().contains("Duplicated"));

    }

    @Test
    void givenValidVote_whenSave_ThenReturn202() {
        Vote sampleVote = getSampleVote();

        when(this.sessionRepository.findById(anyLong())).thenReturn(Optional.of(sampleVote.getElectionSession()));
        when(this.voteRepository.save(sampleVote)).thenReturn(sampleVote);

        VoteDTO voteDTO = voteService.newVote(ENTITY_MAPPER.map(sampleVote));

        assertEquals(ENTITY_MAPPER.map(sampleVote), voteDTO);

    }

}