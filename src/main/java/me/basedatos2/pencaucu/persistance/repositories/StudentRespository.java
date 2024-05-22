package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository("student")
public interface StudentRespository extends JpaRepository<Student, Long>{
    @Query(value = "SELECT * FROM users WHERE ci = ?1", nativeQuery = true)
    Optional<Student> getStudent(Integer ci);

    @Query(value = """
        INSERT INTO Student (
        ci,
        password,
        name,
        lastname,
        email,
        birthdate,
        score
        ) VALUES (?1, ?2, ?3, ?4, ?5, ?6, 0)
    """, nativeQuery = true)
    void createStudent(
            Integer ci,
            String password,
            String name,
            String lastName,
            String email,
            Date birthdate
    );
}
