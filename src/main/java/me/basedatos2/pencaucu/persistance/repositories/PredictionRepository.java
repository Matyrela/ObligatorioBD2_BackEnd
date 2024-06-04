package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.persistance.entities.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM prediction")
    List<Prediction> getPredictions();

    @Query(nativeQuery = true, value = "INSERT INTO prediction (gameid, points, team1score, team2score, studentid) VALUES (?1, 0, ?2, ?3, ?4)")
    void createPrediction(Integer gameid, Integer team1score, Integer team2score, BigDecimal studentid);

    @Query(nativeQuery = true, value = "SELECT * FROM prediction WHERE gameid = ?1 AND studentid = ?2")
    Optional<Prediction> getUniquePrediction(Integer gameid, BigDecimal studentid);

}
