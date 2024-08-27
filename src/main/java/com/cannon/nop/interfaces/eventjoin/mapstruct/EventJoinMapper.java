package com.cannon.nop.interfaces.eventjoin.mapstruct;

import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.domain.eventjoin.model.EventJoinId;
import com.cannon.nop.interfaces.eventjoin.dto.request.EventJoinDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EventJoinMapper {

    @Mapping(source = "eventJoinDTO", target = "eventJoinId", qualifiedByName = "dtoToEventJoinId")
    @Mapping(source = "formData", target = "formData")
    EventJoin toEntity(EventJoinDto eventJoinDTO);

    @Named("dtoToEventJoinId")
    default EventJoinId mapToEventJoinId(EventJoinDto dto) {
        if (dto == null) {
            return null;
        }
        return EventJoinId.builder()
                .eventUrlUUID(dto.getEventUrlUUID())
                .primaryId(dto.getPrimaryId())
                .build();  // joinId는 생성 시점에서 설정되지 않을 수 있으므로 생략 가능
    }
}
