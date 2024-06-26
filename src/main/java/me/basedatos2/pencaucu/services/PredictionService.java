package me.basedatos2.pencaucu.services;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Prediction.Predictiondto;
import me.basedatos2.pencaucu.persistance.entities.Game;
import me.basedatos2.pencaucu.persistance.repositories.GameRepository;
import me.basedatos2.pencaucu.persistance.repositories.PredictionRepository;
import me.basedatos2.pencaucu.util.StudentUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import me.basedatos2.pencaucu.persistance.entities.Student;

@RequiredArgsConstructor
@Service
public class PredictionService {
    private final PredictionRepository pr;
    private final StudentUtils su;
    private final GameRepository gr;

    public List<Predictiondto.PredictionDto> getPredictions() {
        return pr.getPredictions().stream().map(prediction -> new Predictiondto.PredictionDto(
                prediction.getId(),
                prediction.getGame().getId(),
                prediction.getPoints(),
                prediction.getTeam1score(),
                prediction.getTeam2score(),
                prediction.getStudent().getId())).collect(Collectors.toList());
    }

    @Transactional
    public void createPrediction(Predictiondto.CreatePredictionDto predictionDto) throws RuntimeException {

        Optional<Game> optionalGame = gr.getGame(predictionDto.matchid());
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();

            LocalDateTime now = LocalDateTime.now();

            if (now.toLocalDate().isAfter(game.getDate())) {
                throw new RuntimeException("Cannot create prediction for a game that has already happened");
            } else if (now.toLocalDate().isEqual(game.getDate()) && now.toLocalTime().isAfter(game.getTime())) {
                throw new RuntimeException("Cannot create prediction for a game that has already happened");
            }

            long hoursUntilGame = ChronoUnit.HOURS.between(game.getTime(), now);
            if ((now.toLocalDate().equals(game.getDate())) && (hoursUntilGame <= 1)
                    && (game.getTime().isAfter(now.toLocalTime()))) {
                throw new RuntimeException("Cannot create prediction less than an hour before the game starts");
            }
            Student student = su.getStudentFromRequest();
            if (pr.getUniquePrediction(predictionDto.matchid(), student.getId()).isPresent()) {
                pr.updateScores(predictionDto.matchid(), predictionDto.team1score(), predictionDto.team2score());
            } else {
                pr.createPrediction(predictionDto.matchid(), predictionDto.team1score(), predictionDto.team2score(),
                student.getId());
            }   
        } else {
            throw new RuntimeException("Game not found");
        }
    }
}
