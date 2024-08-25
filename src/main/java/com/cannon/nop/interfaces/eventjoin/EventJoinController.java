package com.cannon.nop.interfaces.eventjoin;


import com.cannon.nop.application.EventJoinService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.config.ApiResponse;
import com.cannon.nop.interfaces.event.dto.EventDTO;
import com.cannon.nop.interfaces.event.mapstruct.EventMapper;
import com.cannon.nop.interfaces.eventjoin.dto.EventJoinDTO;
import com.cannon.nop.interfaces.eventjoin.mapstruct.EventJoinMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nop/v1/event")
public class EventJoinController {

    private static final Logger log = LoggerFactory.getLogger(EventJoinController.class);
    private final EventJoinService eventJoinService;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final EventJoinMapper eventJoinMapper;

    @GetMapping("{eventUrlUUID}")
    public ResponseEntity<EventDTO> getEventInformation(@PathVariable String eventUrlUUID) {
        Event event = eventService.getEventQuestionForm(eventUrlUUID);
        EventDTO responseDTO = eventMapper.toDto(event);
        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("{eventUrlUUID}/join")
    public ResponseEntity<ApiResponse> joinEvent(@PathVariable String eventUrlUUID, @RequestBody EventJoinDTO eventJoinDTO){
        log.info("이벤트 정보 받는 테스트: {}",eventUrlUUID);
        eventJoinDTO.setEventUrlUUID(eventUrlUUID);
        log.info("이벤트 정보 받는 테스트: {}",eventJoinDTO);
        eventJoinService.joinEvent(eventJoinMapper.toEntity(eventJoinDTO));
        return ResponseEntity.ok(new ApiResponse(true,"이벤트 참여에 성공했습니다."));
    }
}
