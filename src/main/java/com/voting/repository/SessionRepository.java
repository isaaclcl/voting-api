package com.voting.repository;

import com.voting.modal.ElectionSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<ElectionSession, Long> {
}
