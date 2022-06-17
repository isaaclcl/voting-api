package com.voting.service;

import com.voting.modal.ElectionSession;
import com.voting.repository.SessionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SessionService {

    private final SessionRepository sessionRepository;

    public ElectionSession newSession(ElectionSession electionSession) {
        electionSession.setExpireDate((electionSession.getExpireDate() != null ? electionSession.getExpireDate() : ZonedDateTime.now().plusMinutes(1L)));
        return this.sessionRepository.save(electionSession);
    }

    public List<ElectionSession> findAll() {
        return this.sessionRepository.findAll();
    }

}
