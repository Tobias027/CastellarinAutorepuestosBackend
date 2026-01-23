package com.castellarin.autorepuestos.domain.dto.auth_dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
