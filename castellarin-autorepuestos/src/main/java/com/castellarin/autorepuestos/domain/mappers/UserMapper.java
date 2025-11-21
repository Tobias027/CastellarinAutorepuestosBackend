package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.auth_dtos.SignUpResponse;
import com.castellarin.autorepuestos.domain.entity.User;

public class UserMapper {
    public static SignUpResponse toResponse(User user) {
        return new SignUpResponse(
            user.getUsername(),
            user.getFirstName(),
            user.getLastName()
        );
    }
}
