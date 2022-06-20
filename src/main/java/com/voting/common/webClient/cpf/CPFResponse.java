package com.voting.common.webClient.cpf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CPFResponse {

    private String status;
}
