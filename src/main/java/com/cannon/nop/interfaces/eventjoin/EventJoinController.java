package com.cannon.nop.interfaces.eventjoin;


import com.cannon.nop.application.EventJoinService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.event.dto.EventDTO;
import com.cannon.nop.interfaces.event.mapstruct.EventMapper;
import com.cannon.nop.interfaces.eventjoin.dto.EventJoinDTO;
import com.cannon.nop.interfaces.eventjoin.mapstruct.EventJoinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nop/v1/events")
public class EventJoinController {

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
    public ResponseEntity<Void> joinEvent(@PathVariable String eventUrlUUID, EventJoinDTO eventJoinDTO){
        eventJoinDTO.setEventUrlUUID(eventUrlUUID);
        eventJoinService.joinEvent(eventJoinMapper.toEntity(eventJoinDTO));
        return ResponseEntity.ok().build();
    }
}
