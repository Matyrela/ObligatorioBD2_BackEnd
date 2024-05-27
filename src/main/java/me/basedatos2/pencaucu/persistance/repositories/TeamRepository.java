package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM team" )
    List<Team> getTeams();
    @Query(nativeQuery = true, value = "SELECT * FROM team WHERE countryid = ?1")
    Optional<Team> getTeamsById(Integer id);
    @Query(nativeQuery = true, value = "INSERT INTO team (countryid, name, country) VALUES (?1, ?2, ?3)")
    void insertTeam(Integer countryid, String name, String country);
}
