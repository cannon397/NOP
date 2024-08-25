package com.cannon.nop.interfaces.eventjoin.mapstruct;

import com.cannon.nop.domain.eventjoin.model.EventAnswerForm;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.domain.eventjoin.model.EventJoinId;
import com.cannon.nop.interfaces.eventjoin.dto.EventJoinDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventJoinMapper {

    @Mapping(source = "eventJoinDTO", target = "eventJoinId", qualifiedByName = "dtoToEventJoinId")
    @Mapping(source = "formData", target = "formDatas", qualifiedByName = "stringListToEventAnswerFormList")
    EventJoin toEntity(EventJoinDTO eventJoinDTO);

    @Named("stringToForm")
    default EventAnswerForm stringToForm(String data) {
        return new EventAnswerForm(data);
    }

    @Named("stringListToEventAnswerFormList")
    default List<EventAnswerForm> stringListToEventAnswerFormList(List<String> data) {
        return Optional.ofNullable(data).orElseGet(Collections::emptyList)
                .stream()
                .map(this::stringToForm)
                .collect(Collectors.toList());
    }
    @Named("dtoToEventJoinId")
    default EventJoinId mapToEventJoinId(EventJoinDTO dto) {
        if (dto == null) {
            return null;
        }
        return EventJoinId.builder()
                .eventUrlUUID(dto.getEventUrlUUID())
                .primaryId(dto.getPrimaryId())
                .build();  // joinId는 생성 시점에서 설정되지 않을 수 있으므로 생략 가능
    }
}
