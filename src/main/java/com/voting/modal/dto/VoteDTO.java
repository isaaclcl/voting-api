package com.voting.modal.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class VoteDTO implements Serializable {

    private final Long id;

    @NotNull
    private final Long electionSession;

    @NotBlank
    private final String cpf;

    @NotBlank
    private final String choose;

}

