package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository("student")
public interface StudentRespository extends JpaRepository<Student, Long>{
    @Query(value = "SELECT * FROM student WHERE ci = ?1", nativeQuery = true)
    Optional<Student> getStudent(Integer ci);

    @Modifying
    @Query(value = """
        INSERT INTO student (
        ci,
        password,
        name,
        lastname,
        email,
        birthdate,
        score,
        champion,
        secondplace,
        career
        ) VALUES (?1, ?2, ?3, ?4, ?5, ?6, 0, ?7, ?8, ?9)
    """, nativeQuery = true)
    void createStudent(
            Integer ci,
            String password,
            String name,
            String lastName,
            String email,
            Date birthdate,
            Integer championId,
            Integer secondPlaceId,
            Integer careerId

    );
}
