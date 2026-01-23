package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.auth_dtos.LoginRequest;
import com.castellarin.autorepuestos.domain.dto.auth_dtos.SignUpRequest;
import com.castellarin.autorepuestos.domain.dto.auth_dtos.SignUpResponse;
import com.castellarin.autorepuestos.domain.entity.User;
import com.castellarin.autorepuestos.domain.mappers.UserMapper;
import com.castellarin.autorepuestos.service.AuthService.AuthenticationService;
import com.castellarin.autorepuestos.service.AuthService.JwtServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtServiceImpl jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest registerRequest) {
        User registeredUser = authenticationService.signup(registerRequest);
        SignUpResponse signUpResponse = UserMapper.toResponse(registeredUser);
        return ResponseEntity.created(null).body(signUpResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> authenticate(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        User authenticatedUser = authenticationService.authenticate(loginRequest);

        if(authenticatedUser == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Debes iniciar sesion");
        }
        
        String jwtToken = jwtService.generateToken(authenticatedUser);

        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", jwtToken)
                .httpOnly(true)
                .secure(true) //TODO poner true para que sea https
                .sameSite("None") //STRICT
                .path("/")
                .maxAge(86400)
                .build();

        Map<String,String> body = new HashMap<>();
        body.put("message","Login exitoso.");

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .body(body);
    }
}
