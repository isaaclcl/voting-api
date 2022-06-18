package com.voting.controller;

import com.voting.modal.dto.ElectionSessionDTO;
import com.voting.service.SessionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.voting.constant.ApiConstants.ENDPOINT_SESSIONS;

@RestController
@RequestMapping(ENDPOINT_SESSIONS)
@Getter
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public List<ElectionSessionDTO> listAll() {
        return this.sessionService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ElectionSessionDTO newSession(@Valid @RequestBody ElectionSessionDTO electionSession) {
        return this.sessionService.newSession(electionSession);
    }


}
