package com.voting.service;

import com.voting.modal.Session;
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

    public Session newSession(Session session) {
        session.setExpireDate((session.getExpireDate() != null ? session.getExpireDate() : ZonedDateTime.now().plusMinutes(1L)));
        return this.sessionRepository.save(session);
    }

    public List<Session> findAll() {
        return this.sessionRepository.findAll();
    }

}
