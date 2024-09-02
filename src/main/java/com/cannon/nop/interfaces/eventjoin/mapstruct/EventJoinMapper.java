package com.cannon.nop.interfaces.eventjoin.mapstruct;

import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.domain.eventjoin.model.EventJoinId;
import com.cannon.nop.interfaces.eventjoin.dto.request.EventJoinRequest;
import com.cannon.nop.interfaces.eventjoin.dto.response.EventJoinResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EventJoinMapper {

    @Mapping(target = "datetime", ignore = true)
    @Mapping(source = "eventJoinRequest", target = "eventJoinId", qualifiedByName = "dtoToEventJoinId")
    @Mapping(source = "formData", target = "formData")
    EventJoin toEntity(EventJoinRequest eventJoinRequest);

    @Named("dtoToEventJoinId")
    default EventJoinId mapToEventJoinId(EventJoinRequest dto) {
        if (dto == null) {
            return null;
        }
        return EventJoinId.builder()
                .eventUrlUUID(dto.getEventUrlUUID())
                .primaryId(dto.getPrimaryId())
                .build();  // joinId는 생성 시점에서 설정되지 않을 수 있으므로 생략 가능
    }

    @Mapping(source = "eventJoin", target = "primaryId", qualifiedByName = "EntityToPrimaryId")
    @Mapping(source = "formData", target = "formData")
    EventJoinResponse toResponseDto(EventJoin eventJoin);

    @Named("EntityToPrimaryId")
    default String EntityToPrimaryId(EventJoin eventJoin){
        return eventJoin.getEventJoinId().getPrimaryId();
    }
}
