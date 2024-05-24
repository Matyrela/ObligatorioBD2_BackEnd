package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM team" )
    List<Team> getTeams();
    @Query(nativeQuery = true, value = "SELECT * FROM team WHERE LOWER(country) = LOWER(?1) AND LOWER(name) = LOWER(?2)")
    boolean getTeamsByCountryAndName(String country, String name);
    @Query(nativeQuery = true, value = "INSERT INTO team (name, country) VALUES (?1, ?2)")
    void insertTeam(String name, String country);
}
