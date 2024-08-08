package com.cannon.nop.interfaces.event.mapstruct;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.event.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = EventFormMapper.class)
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "formData", target = "eventQuestionForms", qualifiedByName = "stringsToForms")
    Event toModel(EventDTO eventDto);

    @Mapping(source = "eventQuestionForms", target = "formData", qualifiedByName = "formsToStrings")
    EventDTO toDto(Event event);
}
