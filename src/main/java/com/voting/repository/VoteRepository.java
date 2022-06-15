package com.voting.repository;

import com.voting.modal.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {
    Vote findVoteBySession_Agenda_IdAndCPF(String agendaId, String CPF);

    Long countVoteBySession_Agenda_IdAndValue(String agendaId, String value);

}
