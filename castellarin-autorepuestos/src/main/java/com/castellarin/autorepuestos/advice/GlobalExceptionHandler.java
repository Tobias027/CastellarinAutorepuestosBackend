package com.castellarin.autorepuestos.advice;

import com.castellarin.autorepuestos.exceptions.BadCredentialsException;
import com.castellarin.autorepuestos.exceptions.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map> handleBadCredentialsException(BadCredentialsException ex){

        Map<String,Object> errorDetails = new HashMap<>();

        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", "Bad credentials");
        errorDetails.put("message", "El email o la contraseña es incorrecto");

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String,Object>> handleConflictException(ConflictException ex){

        Map<String,Object> errorDetails = new HashMap<>();

        errorDetails.put("status", HttpStatus.CONFLICT.value());
        errorDetails.put("error", "Conflict");
        errorDetails.put("message", "El email ya está asociado a una cuenta");

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
