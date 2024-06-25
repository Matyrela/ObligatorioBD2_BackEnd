package me.basedatos2.pencaucu.services.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.auth.Auth;
import me.basedatos2.pencaucu.persistance.entities.Admin;
import me.basedatos2.pencaucu.persistance.entities.Student;
import me.basedatos2.pencaucu.persistance.repositories.AdminRepository;
import me.basedatos2.pencaucu.persistance.repositories.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    public boolean Login(Auth.LoginRequest req) throws RuntimeException{
        Optional<Student> studentOptional = studentRepository.getStudent(req.ci());
        Optional<Admin> adminOptional = adminRepository.getAdmin(req.ci());

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if(!isCorrectPassword(req.password(), student.getPassword())){
                throw new RuntimeException("Usuario o contraseña incorrectos");
            }
            return false;

        } else if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if(!isCorrectPassword(req.password(), admin.getPassword())){
                throw new RuntimeException("Usuario o contraseña incorrectos");
            }
            return true;
        } else {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
    }

    @Transactional
    public void Register(Auth.RegisterRequest req) throws RuntimeException{
        if(studentRepository.getStudent(req.ci()).isPresent()){
            throw new RuntimeException("Cedula ya registrada");
        }

        studentRepository.createStudent(
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

    @Transactional
    public void AdminRegister(Auth.AdminRegisterRequest req) throws RuntimeException{
        if(adminRepository.getAdmin(req.ci()).isPresent()){
            throw new RuntimeException("Cedula ya registrada");
        }

        adminRepository.createAdmin(
                req.ci(),
                EncodePassword(req.password()),
                req.birthdate(),
                req.name(),
                req.lastname(),
                req.email()
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
        Optional<Student> optionalStudent = studentRepository.getStudent(ci);
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(student.getId().toString())
                    .password(student.getPassword())
                    .roles("USER")
                    .build();
        }else{
            Optional<Admin> optionalAdmin = adminRepository.getAdmin(ci);
            if(optionalAdmin.isPresent()){
                Admin admin = optionalAdmin.get();
                return org.springframework.security.core.userdetails.User.builder()
                        .username(admin.getId().toString())
                        .password(admin.getPassword())
                        .roles("ADMIN")
                        .build();
            }else{
                throw new RuntimeException("Usuario no encontrado");
            }
        }
    }

}
