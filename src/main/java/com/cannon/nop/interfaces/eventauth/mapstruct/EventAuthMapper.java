package com.cannon.nop.interfaces.eventauth.mapstruct;


import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.interfaces.eventauth.dto.EventAuthDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventAuthMapper {

    EventAuthDTO toDto(EventAuth eventAuth);
}
