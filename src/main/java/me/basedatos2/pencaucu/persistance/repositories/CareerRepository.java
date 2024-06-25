package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Integer> {

    @Modifying
    @Query(value = "INSERT INTO career (name) VALUES (?1)", nativeQuery = true)
    public void createCareer(String name);

    @Modifying
    @Query(value = "DELETE FROM career WHERE id = ?1", nativeQuery = true)
    public void deleteCareer(Integer id);

    @Query(value = "SELECT * FROM career WHERE id = ?1", nativeQuery = true)
    Career getCareer(Integer id);

    @Query(value = "SELECT * FROM career", nativeQuery = true)
    public List<Career> getCareers();

    @Query(value = "SELECT * FROM career WHERE name = ?1", nativeQuery = true)
    Optional<Career> getUniqueCareer(String name);
}
