package com.cannon.nop.interfaces.event;


import com.cannon.nop.application.EventResultService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.interfaces.config.jsonview.Views;
import com.cannon.nop.interfaces.event.admin.dto.response.EventAdminResponse;
import com.cannon.nop.interfaces.event.admin.mapstruct.EventAdminMapper;
import com.cannon.nop.interfaces.event.dto.request.EventRequest;
import com.cannon.nop.interfaces.event.dto.response.EventResultResponse;
import com.cannon.nop.interfaces.event.mapstruct.EventMapper;
import com.cannon.nop.interfaces.event.mapstruct.EventResultMapper;
import com.cannon.nop.interfaces.validator.ValidateUUID;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("organizer")
public class EventController {

    private final EventResultMapper eventResultMapper;
    private final EventMapper eventMapper;
    private final EventAdminMapper eventAdminMapper;
    private final EventService eventService;
    private final EventResultService eventResultService;

    @PostMapping
    public ResponseEntity<EventAdminResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        Event event = eventService.createEvent(eventMapper.toModel(eventRequest));
        EventAdminResponse eventAdminResponse = eventAdminMapper.toDto(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/event/{eventUrlUUID}")
                .buildAndExpand(eventAdminResponse.getEventUrlUUID())
                .toUri();
        return ResponseEntity.created(location).body(eventAdminResponse);
    }

    @JsonView(Views.WhenGetEventResult.class)
    @GetMapping("{eventUrlUUID}")
    public EventResultResponse getEventResult(@PathVariable @ValidateUUID String eventUrlUUID) {
        EventResult eventResult = eventResultService.getEventResult(eventUrlUUID);
        EventResultResponse eventResultResponse = eventResultMapper.toDto(eventResult);
        return eventResultResponse;
    }
}
