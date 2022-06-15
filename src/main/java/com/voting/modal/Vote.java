package com.voting.modal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Vote {
    @Id
    private String id;

    @NotNull
    private Session session;

    @NotBlank
    private String CPF;

    @NotBlank
    private String value;

}

