package me.basedatos2.pencaucu.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.persistance.entities.Student;
import me.basedatos2.pencaucu.persistance.repositories.StudentRespository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class StudentUtils {
    private final StudentRespository studentRespository;
    private final JWTUtils jwtUtils;

    public Student getStudentFromRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = jwtUtils.getTokenFromRequest(request);
        if (token != null && jwtUtils.validateToken(token)) {
            Long ci = Long.valueOf(jwtUtils.getSubjectFromToken(token));
            return studentRespository.findById(ci).orElse(null);
        }
        return null;
    }
}