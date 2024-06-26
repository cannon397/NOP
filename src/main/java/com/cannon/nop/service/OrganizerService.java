package com.cannon.nop.service;

import com.cannon.nop.dto.OrganizerRequestDTO;
import com.cannon.nop.dto.OrganizerResponseDTO;
import com.cannon.nop.entity.Organizer;
import com.cannon.nop.mapper.OrganizerMapper;
import com.cannon.nop.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizerService {
    private final OrganizerRepository organizerRepository;
    private final OrganizerMapper organizerMapper;
    @Autowired
    public OrganizerService(OrganizerRepository organizerRepository, OrganizerMapper organizerMapper) {
        this.organizerRepository = organizerRepository;
        this.organizerMapper = organizerMapper;
    }

    public OrganizerResponseDTO createURL(OrganizerRequestDTO organizerRequestDTO){
        Organizer organizer = organizerMapper.toEntity(organizerRequestDTO);
        organizerRepository.save(organizer);
        return organizerMapper.toResponseDTO(organizer);
    }


}

