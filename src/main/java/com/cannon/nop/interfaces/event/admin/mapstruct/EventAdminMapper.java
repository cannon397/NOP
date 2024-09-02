package com.cannon.nop.interfaces.event.admin.mapstruct;


import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.event.admin.dto.response.EventAdminResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventAdminMapper {

    EventAdminResponse toDto(Event event);
}
