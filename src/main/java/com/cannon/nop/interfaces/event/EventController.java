package com.cannon.nop.interfaces.event;



import com.cannon.nop.application.EventResultService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.interfaces.config.ApiPaths;
import com.cannon.nop.interfaces.config.jsonview.Views;
import com.cannon.nop.interfaces.event.dto.request.EventRequest;
import com.cannon.nop.interfaces.event.mapstruct.EventMapper;
import com.cannon.nop.interfaces.event.mapstruct.EventResultMapper;
import com.cannon.nop.interfaces.event.admin.dto.response.EventAdminResponse;
import com.cannon.nop.interfaces.event.admin.mapstruct.EventAdminMapper;
import com.cannon.nop.interfaces.event.dto.response.EventResultResponse;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPaths.BASE_URL+"/organizer")
@Slf4j
public class EventController {

    private final EventResultMapper eventResultMapper;
    private final EventMapper eventMapper;
    private final EventAdminMapper eventAdminMapper;
    private final EventService eventService;
    private final EventResultService eventResultService;

    @PostMapping
    public ResponseEntity<EventAdminResponse> createEvent(@RequestBody EventRequest eventRequest) {
        Event event = eventService.createEvent(eventMapper.toModel(eventRequest));
        EventAdminResponse eventAdminResponse = eventAdminMapper.toDto(event);
//        event를EventAuthReponse로변경해야함
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ApiPaths.BASE_URL + "/event/{eventUrlUUID}")
                .buildAndExpand(eventAdminResponse.getEventUrlUUID())
                .toUri();
        return ResponseEntity.created(location).body(eventAdminResponse);
    }

    @JsonView(Views.WhenGetEventResult.class)
    @PostMapping("{eventUrlUUID}")
    public EventResultResponse getEventResult(@PathVariable String eventUrlUUID) {
//        eventUrlUUID를 토큰에서 가져와야함;
        log.info("Get event result for admin url {}", eventUrlUUID);

        EventResult eventResult = eventResultService.getEventResult(eventUrlUUID);
        EventResultResponse eventResultResponse = eventResultMapper.toDto(eventResult);
        return eventResultResponse;
    }
}
