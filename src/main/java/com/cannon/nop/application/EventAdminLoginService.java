package com.cannon.nop.application;


import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.auth.JwtProvider;
import com.cannon.nop.interfaces.auth.dto.request.AuthRequest;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventAdminLoginService {
    private final JwtProvider jwtProvider;
    private final EventRepository eventRepository;

    public String login(AuthRequest authRequest) {
        Event event = eventRepository.findById(authRequest.eventUrlUUID()).orElseThrow(()->new ApiException(ErrorCode.UNAUTHORIZED_ERROR));
        if(!event.getAdminKeyUUID().equals(authRequest.adminKeyUUID())) {
            throw new ApiException(ErrorCode.UNAUTHORIZED_ERROR);
        }
        return jwtProvider.generateToken(event.getEventUrlUUID());
    }
}
