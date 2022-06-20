package com.voting.repository;

import com.voting.modal.tables.ElectionSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<ElectionSession, Long> {
    List<ElectionSession> findElectionSessionsByResultSentFalseAndExpireDateBefore(ZonedDateTime expiredDate);

}
