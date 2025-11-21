package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.auth_dtos.AuthResponse;
import com.castellarin.autorepuestos.domain.dto.auth_dtos.LoginRequest;
import com.castellarin.autorepuestos.domain.dto.auth_dtos.SignUpRequest;
import com.castellarin.autorepuestos.domain.dto.auth_dtos.SignUpResponse;
import com.castellarin.autorepuestos.domain.entity.User;
import com.castellarin.autorepuestos.domain.mappers.UserMapper;
import com.castellarin.autorepuestos.service.AuthService.AuthenticationService;
import com.castellarin.autorepuestos.service.AuthService.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtServiceImpl jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest registerRequest) {
        User registeredUser = authenticationService.signup(registerRequest);
        SignUpResponse signUpResponse = UserMapper.toResponse(registeredUser);
        return ResponseEntity.created(null).body(signUpResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest loginRequest){
        User authenticatedUser = authenticationService.authenticate(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        AuthResponse authResponse = new AuthResponse(jwtToken);
        return ResponseEntity.ok(authResponse);
    }
}
