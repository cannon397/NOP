package com.cannon.nop.interfaces.eventjoin.mapstruct;

import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.interfaces.eventjoin.dto.EventJoinDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventJoinMapper {
    EventJoin toEntity(EventJoinDTO eventJoinDTO);
}
