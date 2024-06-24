package me.basedatos2.pencaucu.dto.auth;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class Auth {

    // LOGIN RECORD -----------------------------------------------------------

    public record LoginRequest(
            Integer ci,
            String password
    ) {}

    @Builder
    public record LoginResponse(
            HttpStatus status,
            String message,
            String token,
            String role
    ) {}

    // REGISTER RECORD --------------------------------------------------------

    public record RegisterRequest(
            Integer ci,
            String password,
            String name,
            String lastName,
            String email,
            Date birthdate,
            Integer champion,
            Integer secondPlace,
            Integer career
    ) {}

    public record AdminRegisterRequest(
            Integer ci,
            String password,
            Date birthdate,
            String name,
            String lastname,
            String email
    ) {}

    @Builder
    public record RegisterResponse(
            HttpStatus status,
            Boolean created,
            String message
    ) {}

    // TOKEN RECORD -----------------------------------------------------------

    public record TokenRequest(
            String token
    ) {}

}
