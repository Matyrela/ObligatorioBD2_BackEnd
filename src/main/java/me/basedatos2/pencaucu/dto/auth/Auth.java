package me.basedatos2.pencaucu.dto.auth;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public class Auth {
    public record LoginRequest(
            Integer ci,
            String password
    ) {}

    @Builder
    public record LoginResponse(
            HttpStatus status,
            String message,
            String token
    ) {}

    public record TokenRequest(
            String token
    ) {}

}
