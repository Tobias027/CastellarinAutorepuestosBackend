package com.castellarin.autorepuestos.domain.dto.auth_dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {
    private String email;
    private String firstName;
    private String lastName;
}
