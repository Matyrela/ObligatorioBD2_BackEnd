package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Prediction;
import me.basedatos2.pencaucu.persistance.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM prediction")
    List<Prediction> getPredictions();

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO prediction (game, points, team1score, team2score, student) VALUES (?1, 0, ?2, ?3, ?4)")
    void createPrediction(Integer gameid, Integer team1score, Integer team2score, Long studentid);

    @Query(nativeQuery = true, value = "SELECT * FROM prediction WHERE game = ?1 AND student = ?2")
    Optional<Prediction> getUniquePrediction(Integer gameid, Long studentid);

    @Query(nativeQuery = true, value = """
        SELECT * FROM prediction WHERE game = ?1
    """)
    List<Prediction> getAffectedPredictions(Integer gameid);

    @Modifying
    @Query(nativeQuery = true, value = """
        UPDATE prediction SET points = :i WHERE id = :id
    """)
    void updatePoints(Integer id, int i);

    @Query(nativeQuery = true, value = """
        SELECT * FROM prediction WHERE game = :id AND student = :ci
    """)
    Prediction getPredictionByGameIdAndStudent(Integer id, Long ci);

    @Modifying
    @Query(nativeQuery = true, value = """
        UPDATE prediction SET team1score = ?2, team2score = ?3 WHERE game = ?1
    """)
    void updateScores(Integer gameid, Integer team1score, Integer team2score);
}
