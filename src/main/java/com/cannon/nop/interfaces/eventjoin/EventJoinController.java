package com.cannon.nop.interfaces.eventjoin;


import com.cannon.nop.application.EventJoinService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.config.ApiResponse;
import com.cannon.nop.interfaces.event.dto.response.EventResponseDto;
import com.cannon.nop.interfaces.event.mapstruct.EventMapper;
import com.cannon.nop.interfaces.eventjoin.dto.request.EventJoinDto;
import com.cannon.nop.interfaces.eventjoin.mapstruct.EventJoinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nop/v1/event")
public class EventJoinController {

    private final EventJoinService eventJoinService;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final EventJoinMapper eventJoinMapper;

    @GetMapping("{eventUrlUUID}")
    public ResponseEntity<EventResponseDto> getEventInformation(@PathVariable String eventUrlUUID) {
        Event event = eventService.getEventQuestionForm(eventUrlUUID);
        EventResponseDto eventResponseDto = eventMapper.toDto(event);
        return ResponseEntity.ok(eventResponseDto);
    }
    @PostMapping("{eventUrlUUID}/join")
    public ResponseEntity<ApiResponse> joinEvent(@PathVariable String eventUrlUUID, @RequestBody EventJoinDto eventJoinDTO){
        eventJoinDTO.setEventUrlUUID(eventUrlUUID);
        eventJoinService.joinEvent(eventJoinMapper.toEntity(eventJoinDTO));
        return ResponseEntity.ok(new ApiResponse(true,"이벤트 참여에 성공했습니다."));
    }
}
