package me.basedatos2.pencaucu.persistance.repositories;

import me.basedatos2.pencaucu.dto.score.ScoreDto;
import me.basedatos2.pencaucu.persistance.entities.Prediction;
import me.basedatos2.pencaucu.persistance.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM prediction")
    List<Prediction> getPredictions();

    @Query(nativeQuery = true, value = "INSERT INTO prediction (game, points, team1score, team2score, student) VALUES (?1, 0, ?2, ?3, ?4)")
    void createPrediction(Integer gameid, Integer team1score, Integer team2score, Long studentid);

    @Query(nativeQuery = true, value = "SELECT * FROM prediction WHERE game = ?1 AND student = ?2")
    Optional<Prediction> getUniquePrediction(Integer gameid, Long studentid);
}