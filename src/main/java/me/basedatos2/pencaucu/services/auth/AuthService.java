package me.basedatos2.pencaucu.services.auth;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.basedatos2.pencaucu.dto.auth.Auth;
import me.basedatos2.pencaucu.persistance.entities.Student;
import me.basedatos2.pencaucu.persistance.repositories.StudentRespository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final StudentRespository studentRespository;

    public void Login(Auth.LoginRequest req) throws RuntimeException{
        Student student = studentRespository.getStudent(req.ci()).orElseThrow(
                () -> new RuntimeException("Usuario o contraseña incorrectos")
        );

        if(!isCorrectPassword(req.password(), student.getPassword())){
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
    }

    @Transactional
    public void Register(Auth.RegisterRequest req) throws RuntimeException{
        if(studentRespository.getStudent(req.ci()).isPresent()){
            throw new RuntimeException("Cedula ya registrada");
        }

        studentRespository.createStudent(
                req.ci(),
                EncodePassword(req.password()),
                req.name(),
                req.lastName(),
                req.email(),
                req.birthdate(),
                req.champion(),
                req.secondPlace(),
                req.career()
        );
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
        Student student = studentRespository.getStudent(ci).orElseThrow(
            () -> new RuntimeException("Usuario no encontrado")
        );
        return org.springframework.security.core.userdetails.User.builder()
            .username(student.getId().toString())
            .password(student.getPassword())
            .roles("USER")
            .build();
    }

}
