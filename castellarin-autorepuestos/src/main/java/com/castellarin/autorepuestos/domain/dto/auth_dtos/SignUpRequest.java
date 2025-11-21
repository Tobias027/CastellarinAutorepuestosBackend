package com.castellarin.autorepuestos.domain.dto.auth_dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
}
