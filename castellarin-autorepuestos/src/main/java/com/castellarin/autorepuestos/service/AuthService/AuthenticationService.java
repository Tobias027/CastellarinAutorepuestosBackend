package com.castellarin.autorepuestos.service.AuthService;

import com.castellarin.autorepuestos.domain.dto.auth_dtos.LoginRequest;
import com.castellarin.autorepuestos.domain.dto.auth_dtos.SignUpRequest;
import com.castellarin.autorepuestos.domain.entity.Role;
import com.castellarin.autorepuestos.domain.entity.User;
import com.castellarin.autorepuestos.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public User signup(SignUpRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("passwords do not match");
        };
        User user = new User(
            null,
            registerRequest.getEmail(),
            passwordEncoder.encode(registerRequest.getPassword()),
            registerRequest.getFirstName(),
            registerRequest.getLastName(),
            Role.USUARIO.name(),
            true,
            null,
            null,
            null
        );
        return userRepository.save(user);
    }

    public User authenticate(LoginRequest input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return user;
    }
}
