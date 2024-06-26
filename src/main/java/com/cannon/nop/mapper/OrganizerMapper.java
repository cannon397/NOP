package com.cannon.nop.mapper;

import com.cannon.nop.dto.OrganizerRequestDTO;
import com.cannon.nop.dto.OrganizerResponseDTO;
import com.cannon.nop.entity.Organizer;
import com.cannon.nop.util.Util;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrganizerMapper {
    @Mapping(target = "uuid", expression = "java(createUUID())")
    @Mapping(target = "secretKey", expression = "java(createUUID())")
    Organizer toEntity(OrganizerRequestDTO OrganizerRequestDTO);

    OrganizerResponseDTO toResponseDTO(Organizer organizer);

    // 예제 계산 메소드
    default String createUUID() {
        return Util.getUUID();
    }
}
