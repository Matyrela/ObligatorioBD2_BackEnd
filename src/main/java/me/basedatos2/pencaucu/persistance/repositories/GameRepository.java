package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository("game")
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT * FROM game
                    WHERE date = :date
                    AND "time" = :time
                    AND team1id = :team1
                    AND team2id = :team2
                    AND stadium = :stadium
            """
    )
    Optional<Game> getUniqueGame(
            LocalDate date,
            LocalTime time,
            Integer team1,
            Integer team2,
            String stadium
    );

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO game (date, time, team1id, team2id, stadium, team1score, team2score)
                    VALUES (:date, :time, :team1, :team2, :stadium, 0, 0)
            """
    )
    void createGame(
            LocalDate date,
            LocalTime time,
            Integer team1,
            Integer team2,
            String stadium
    );


    @Query(nativeQuery = true, value = """
        SELECT * FROM game;
    """)
    List<Game> getAllGames();

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    UPDATE game
                    SET team1score = :score1, team2score = :score2
                    WHERE gameid = :gameid
            """
    )
    void updateScores(Integer gameid, Integer score1, Integer score2);

    @Query(nativeQuery = true, value = "SELECT * FROM game WHERE gameid = ?1")
    Optional<Game> getGame(Integer matchid);
}
