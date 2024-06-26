package com.cannon.nop.controller;


import com.cannon.nop.dto.OrganizerRequestDTO;
import com.cannon.nop.dto.OrganizerResponseDTO;
import com.cannon.nop.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nop/v1/organizers")
public class OrganizerController {
    OrganizerService organizerService;
    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping
    public OrganizerResponseDTO createURL(@RequestBody OrganizerRequestDTO organizerRequestDTO){
        return organizerService.createURL(organizerRequestDTO);
    }

}
