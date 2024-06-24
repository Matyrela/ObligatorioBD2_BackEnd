package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository("student")
public interface StudentRepository extends JpaRepository<Student, Long>{
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

    @Modifying
    @Query(value = """
        UPDATE student SET score = score + :id WHERE ci = :points;
    """, nativeQuery = true)
    void updatePoints(Long id, Integer points);

    @Query(nativeQuery = true, value = """
            SELECT score FROM student WHERE ci = :ci;
        """)
    Integer getPoints(Long ci);

    @Query(nativeQuery = true, value = """
            SELECT * FROM student ORDER BY score DESC LIMIT 10;
        """)
    List<Student> getGlobalRanking();

    @Query(nativeQuery = true, value = """
            SELECT * FROM student WHERE ci = :ci;
        """)
    Optional<Student> getRank(Long ci);

    @Modifying
    @Query(value = """
        DELETE FROM student WHERE ci = ?1
    """, nativeQuery = true)
    void deleteStudent(Integer ci);
}
