package me.basedatos2.pencaucu.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.auth.Auth;
import me.basedatos2.pencaucu.persistance.repositories.AdminRepository;
import me.basedatos2.pencaucu.persistance.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final StudentRepository studentRepository;

    @Transactional
    public void deleteStudent(Auth.DeleteUserRequest deleteUserRequest){
        studentRepository.deleteStudent(deleteUserRequest.id());
    }

}
