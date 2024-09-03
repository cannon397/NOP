package com.cannon.nop.interfaces.event.mapstruct;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.event.dto.request.EventRequest;
import com.cannon.nop.interfaces.event.dto.response.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "adminKeyUUID", ignore = true)
    @Mapping(target = "eventUrlUUID", ignore = true)
    @Mapping(target = "joinLeft",source = "joinLimit")
    @Mapping(source = "formData", target = "eventQuestionForms")
    Event toModel(EventRequest eventRequest);

    @Mapping(source = "eventQuestionForms", target = "formData")
    EventResponse toDto(Event event);
}
