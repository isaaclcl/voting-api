package com.voting.modal.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class ElectionSessionDTO implements Serializable {

    private final Long id;

    @NotNull
    private final Long agenda;

    private final ZonedDateTime expireDate;

}
