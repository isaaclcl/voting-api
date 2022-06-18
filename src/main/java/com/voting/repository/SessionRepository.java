package com.voting.repository;

import com.voting.modal.tables.ElectionSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<ElectionSession, Long> {
}
