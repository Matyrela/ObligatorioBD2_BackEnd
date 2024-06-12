package me.basedatos2.pencaucu.controller.Admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.auth.Auth;
import me.basedatos2.pencaucu.services.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
@Data

public class AdminController {
    private final AuthService authService;
    @PostMapping("register")
    public Auth.RegisterResponse registerAdmin(
            @RequestBody Auth.AdminRegisterRequest adminRegisterRequest
    ) {
        try {
            authService.AdminRegister(adminRegisterRequest);
            return Auth.RegisterResponse.builder()
                    .status(HttpStatus.CREATED)
                    .created(true)
                    .message("Admin created")
                    .build();
        } catch (RuntimeException e) {
            return Auth.RegisterResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .created(false)
                    .message(e.getMessage())
                    .build();
        }
    }
}
