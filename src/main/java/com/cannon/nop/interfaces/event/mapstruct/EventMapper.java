package com.cannon.nop.interfaces.event.mapstruct;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.event.dto.request.EventRequestDto;
import com.cannon.nop.interfaces.event.dto.response.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EventMapper {


    @Mapping(target = "eventUrlUUID", ignore = true)
    @Mapping(source = "formData", target = "eventQuestionForms")
    Event toModel(EventRequestDto eventRequestDTO);

    @Mapping(source = "eventQuestionForms", target = "formData")
    EventResponseDto toDto(Event event);
}
