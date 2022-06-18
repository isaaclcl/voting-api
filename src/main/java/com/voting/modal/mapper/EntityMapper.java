package com.voting.modal.mapper;

import com.voting.modal.dto.AgendaDTO;
import com.voting.modal.dto.ElectionSessionDTO;
import com.voting.modal.dto.VoteDTO;
import com.voting.modal.tables.Agenda;
import com.voting.modal.tables.ElectionSession;
import com.voting.modal.tables.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;

@Mapper(imports = ZonedDateTime.class, componentModel = "spring")
public interface EntityMapper {

    EntityMapper ENTITY_MAPPER = Mappers.getMapper(EntityMapper.class);

    AgendaDTO map(Agenda agenda);

    Agenda map(AgendaDTO agendaDTO);

    @Mapping(target = "electionSession", source = "electionSession.id")
    VoteDTO map(Vote agenda);

    @Mapping( target = "electionSession.id", source = "electionSession")
    Vote map(VoteDTO agendaDTO);

    @Mapping( target = "agenda", source = "agenda.id")
    ElectionSessionDTO map(ElectionSession electionSession);

    @Mapping(source = "agenda", target = "agenda.id")
    @Mapping(source = "expireDate", target = "expireDate", defaultExpression = "java(ZonedDateTime.now().plusMinutes(1L))")
    ElectionSession map(ElectionSessionDTO electionSessionDTO);

}
