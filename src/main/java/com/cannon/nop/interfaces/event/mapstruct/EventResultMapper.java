package com.cannon.nop.interfaces.event.mapstruct;

import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.interfaces.eventjoin.dto.response.EventJoinResponse;
import com.cannon.nop.interfaces.event.dto.response.EventResultResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EventResultMapper {

    @Mapping(target = "event.formData",source = "event.eventQuestionForms")
    @Mapping(target = "eventJoins", source = "eventJoins", qualifiedByName = "toEventJoinDto")
    EventResultResponse toDto(EventResult eventResult);

    @Named("toEventJoinDto")
    @Mapping(target = "primaryId", source = "eventJoinId.primaryId")
    @Mapping(target = "formData", source = "formData")
    EventJoinResponse toEventJoinDto(EventJoin eventJoin);

}