package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("users")
public interface UserRespository extends JpaRepository<User, Long>{
    @Query(value = "SELECT * FROM users WHERE ci = ?1", nativeQuery = true)
    Optional<User> getUser(Integer ci);
}
