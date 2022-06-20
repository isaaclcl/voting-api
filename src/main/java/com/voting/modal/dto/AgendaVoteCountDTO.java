package com.voting.modal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AgendaVoteCountDTO {

    private Long agendaId;

    private List<VoteCountDTO> votes;

}
