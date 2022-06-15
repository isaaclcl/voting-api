package com.voting.modal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Document
@Getter
@Setter
@EqualsAndHashCode
public class Session {
    @Id
    private final String id;
    @NotNull
    @DocumentReference
    private final Agenda agenda;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime expireDate;

    public Session(String id, Agenda agenda) {
        this.id = id;
        this.agenda = agenda;
    }

}
