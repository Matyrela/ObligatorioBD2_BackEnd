package me.basedatos2.pencaucu.controller.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.auth.Auth;
import me.basedatos2.pencaucu.services.auth.AuthService;
import me.basedatos2.pencaucu.util.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Data
public class AuthController {
    private final AuthService authService;
    private final JWTUtils jwtUtils;

    @PostMapping("login")
    public Auth.LoginResponse login(
            @RequestBody Auth.LoginRequest loginRequest
    ) {
        try {
            authService.Login(loginRequest);
            String token = jwtUtils.generateToken(loginRequest.ci());

            return Auth.LoginResponse.builder()
                .status(HttpStatus.OK)
                .token(token)
                .message("Login successful")
                .build();
        } catch (RuntimeException e) {
            return Auth.LoginResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .build();
        }
    }

    @PostMapping("token")
    public Auth.LoginResponse token(
            @RequestBody Auth.TokenRequest tokenRequest
    ) {
        if(jwtUtils.validateToken(tokenRequest.token())){
            return Auth.LoginResponse.builder()
                .status(HttpStatus.OK)
                .message("Valid token")
                .token(tokenRequest.token())
                .build();
        } else {
            return Auth.LoginResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Invalid token")
                .token(tokenRequest.token())
                .build();
        }
    }

    @PostMapping("token/refresh")
    public Auth.LoginResponse refreshToken(
            @RequestBody Auth.TokenRequest tokenRequest
    ) {
        if(jwtUtils.validateToken(tokenRequest.token())){
            return Auth.LoginResponse.builder()
                .status(HttpStatus.OK)
                .message("Token refreshed")
                .token(jwtUtils.generateToken(Integer.parseInt(jwtUtils.getSubjectFromToken(tokenRequest.token()))))
                .build();
        } else {
            return Auth.LoginResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Invalid token")
                .token(tokenRequest.token())
                .build();
        }
    }
}
