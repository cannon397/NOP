package com.cannon.nop.interfaces.event.mapstruct;

import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.interfaces.event.dto.EventResultDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventResultMapper {
    EventResultDTO toDto(EventResult eventResult);
}