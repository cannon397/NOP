package com.cannon.nop.interfaces.event;


import com.cannon.nop.application.EventResultService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.interfaces.eventauth.mapstruct.EventAuthMapper;
import com.cannon.nop.interfaces.eventauth.dto.EventAuthDTO;
import com.cannon.nop.interfaces.event.dto.EventDTO;
import com.cannon.nop.interfaces.event.dto.EventResultDTO;
import com.cannon.nop.interfaces.event.mapstruct.EventMapper;
import com.cannon.nop.interfaces.event.mapstruct.EventResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nop/v1/organizers")
public class EventController {


    private final EventResultMapper eventResultMapper;
    private final EventMapper eventMapper;
    private final EventAuthMapper eventAuthMapper;
    private final EventService eventService;
    private final EventResultService eventResultService;


    @PostMapping
    public ResponseEntity<EventAuthDTO> createEvent(@RequestBody EventDTO eventDTO) {
        EventAuth eventAuth = eventService.createEvent(eventMapper.toModel(eventDTO));
        EventAuthDTO eventAuthDTO = eventAuthMapper.toDto(eventAuth);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/nop/v1/events/{eventUrlUUID}")
                .buildAndExpand(eventAuthDTO.getEventUrlUUID())
                .toUri();
        return ResponseEntity.created(location).body(eventAuthDTO);
    }

    @PostMapping("{adminUrlUUID}")
    public ResponseEntity<EventResultDTO> getEventResult(@RequestParam String adminUrlUUID, @RequestBody String adminKeyUUID) {

        EventResult eventResult = eventResultService.authenticateAndFetchResults(adminUrlUUID, adminKeyUUID);
        EventResultDTO eventResultDTO = eventResultMapper.toDto(eventResult);
        return ResponseEntity.ok(eventResultDTO);
    }
}
