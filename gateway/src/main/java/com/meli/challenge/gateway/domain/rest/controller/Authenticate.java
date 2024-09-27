package com.meli.challenge.gateway.domain.rest.controller;

import com.meli.challenge.gateway.domain.rest.dto.ResponseTokenDto;
import com.meli.challenge.gateway.domain.rest.dto.UserDto;
import com.meli.challenge.gateway.model.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a dummy class to simulate the authentication process.
 * It should be removed in a real scenario.
 * The authentication process should be done by a real authentication service.
 */

@RestController
public class Authenticate {
    private final JwtUtil jwtUtil;

    public Authenticate(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseTokenDto> authenticate(@RequestBody UserDto user) {
        return ResponseEntity.ok(new ResponseTokenDto(jwtUtil.generateToken(user.getUsername())));
    }
}
