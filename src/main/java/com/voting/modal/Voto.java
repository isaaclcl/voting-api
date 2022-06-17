package com.voting.modal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Voto {

    @Id
    private Long id;

    @JoinColumn
    @ManyToOne
    private ElectionSession electionSession;

}
