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




    @PostMapping("register")
    public Auth.RegisterResponse register(
            @RequestBody Auth.RegisterRequest registerRequest
    ) {
        try {
            authService.Register(registerRequest);
            return Auth.RegisterResponse.builder()
                .status(HttpStatus.CREATED)
                .created(true)
                .message("User created")
                .build();
        } catch (RuntimeException e) {
            return Auth.RegisterResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .created(false)
                .message(e.getMessage())
                .build();
        }
    }


    @PostMapping("login")
    public Auth.LoginResponse login(
            @RequestBody Auth.LoginRequest loginRequest
    ) {
        try {
            boolean check = authService.Login(loginRequest);
            String token = "";
            if (!check){
                token = jwtUtils.generateToken(loginRequest.ci());
            }else{
                token = jwtUtils.generateAdminToken(loginRequest.ci());
            }

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
                .token(null)
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
