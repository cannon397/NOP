package com.cannon.nop.interfaces.event;


import com.cannon.nop.application.EventResultService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.interfaces.event.dto.request.EventRequestDto;
import com.cannon.nop.interfaces.event.dto.response.EventResultDto;
import com.cannon.nop.interfaces.event.mapstruct.EventMapper;
import com.cannon.nop.interfaces.event.mapstruct.EventResultMapper;
import com.cannon.nop.interfaces.eventauth.dto.request.EventAdminKeyDto;
import com.cannon.nop.interfaces.eventauth.dto.response.EventAuthDto;
import com.cannon.nop.interfaces.eventauth.mapstruct.EventAuthMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nop/v1/organizer")
public class EventController {


    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventResultMapper eventResultMapper;
    private final EventMapper eventMapper;
    private final EventAuthMapper eventAuthMapper;
    private final EventService eventService;
    private final EventResultService eventResultService;


    @PostMapping
    public ResponseEntity<EventAuthDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        EventAuth eventAuth = eventService.createEvent(eventMapper.toModel(eventRequestDto));
        EventAuthDto eventAuthDto = eventAuthMapper.toDto(eventAuth);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/nop/v1/event/{eventUrlUUID}")
                .buildAndExpand(eventAuthDto.getEventUrlUUID())
                .toUri();
        return ResponseEntity.created(location).body(eventAuthDto);
    }

    @PostMapping("{adminUrlUUID}")
    public ResponseEntity<EventResultDto> getEventResult(@PathVariable String adminUrlUUID, @RequestBody EventAdminKeyDto eventAdminKeyDto) {
        log.error("adminKey: {}",eventAdminKeyDto.adminKeyUUID());
        EventResult eventResult = eventResultService.authenticateAndFetchResults(adminUrlUUID, eventAdminKeyDto.adminKeyUUID());
        EventResultDto eventResultDto = eventResultMapper.toDto(eventResult);
        return ResponseEntity.ok(eventResultDto);
    }
}
