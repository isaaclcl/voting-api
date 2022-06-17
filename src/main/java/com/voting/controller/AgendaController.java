package com.voting.controller;

import com.voting.modal.Agenda;
import com.voting.service.AgendaService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.voting.constant.ApiConstants.ENDPOINT_AGENDA;

@RestController
@RequestMapping(ENDPOINT_AGENDA)
@Getter
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @GetMapping
    public List<Agenda> listAll() {
        return this.agendaService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agenda newAgenda() {
        return this.agendaService.newAgenda();
    }

}