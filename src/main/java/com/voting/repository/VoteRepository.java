package com.voting.repository;

import com.voting.modal.tables.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findVoteByElectionSession_Agenda_IdAndCpf(Long agendaId, String CPF);

    Long countVoteByElectionSession_Agenda_IdAndChoose(Long agendaId, String choose);

}
