package me.basedatos2.pencaucu.services.auth;

import lombok.AllArgsConstructor;
import me.basedatos2.pencaucu.dto.auth.Auth;
import me.basedatos2.pencaucu.persistance.entities.User;
import me.basedatos2.pencaucu.persistance.repositories.UserRespository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRespository userRespository;

    public void Login(Auth.LoginRequest req) throws RuntimeException{
        User user = userRespository.getUser(req.ci()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        if(!isCorrectPassword(req.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
    }

    public String EncodePassword(String password) {
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength);
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean isCorrectPassword(String password, String hashedPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, hashedPassword);
    }

    public UserDetails loadUserByCI(Integer ci){
        User user = userRespository.getUser(ci).orElseThrow(
            () -> new RuntimeException("User not found")
        );
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getCi().toString())
            .password(user.getPassword())
            .roles("USER")
            .build();
    }

}
