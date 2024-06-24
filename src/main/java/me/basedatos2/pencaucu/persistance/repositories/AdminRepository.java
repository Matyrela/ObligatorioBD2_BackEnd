package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Query(value = "SELECT * FROM admin WHERE ci = ?1", nativeQuery = true)
    Optional<Admin> getAdmin(Integer ci);

    @Modifying
    @Query(value = "INSERT INTO admin (ci, password, birthdate, name, lastname, email) VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    void createAdmin(
            Integer ci,
            String password,
            Date birthdate,
            String name,
            String lastname,
            String email
    );

}
