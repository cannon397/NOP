package com.cannon.nop.interfaces.eventauth.mapstruct;


import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.interfaces.eventauth.dto.response.EventAuthDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventAuthMapper {

    EventAuthDto toDto(EventAuth eventAuth);
}
