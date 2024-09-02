package com.cannon.nop.interfaces.auth;


import com.cannon.nop.application.EventAdminLoginService;
import com.cannon.nop.interfaces.auth.dto.request.AuthRequest;
import com.cannon.nop.interfaces.auth.dto.response.AuthResponse;
import com.cannon.nop.interfaces.config.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.BASE_URL)
public class AuthenticationController {
    private final EventAdminLoginService eventAdminLoginService;

    @PostMapping("login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        String jwtToken = eventAdminLoginService.login(authRequest);
        AuthResponse authResponse = new AuthResponse(jwtToken);
        return authResponse;
    }
}
