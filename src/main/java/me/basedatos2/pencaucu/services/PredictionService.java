package me.basedatos2.pencaucu.services;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Prediction.Predictiondto;
import me.basedatos2.pencaucu.persistance.repositories.PredictionRepository;
import me.basedatos2.pencaucu.util.StudentUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import me.basedatos2.pencaucu.persistance.entities.Student;

@RequiredArgsConstructor
@Service
public class PredictionService {
    private final PredictionRepository pr;
    private final StudentUtils su;

    public List<Predictiondto.PredictionDto> getPredictions() {
        return pr.getPredictions().stream().map(prediction -> new Predictiondto.PredictionDto(
                prediction.getId(),
                prediction.getGame().getId(),
                prediction.getPoints(),
                prediction.getTeam1score(),
                prediction.getTeam2score(),
                prediction.getStudent().getId()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void createPrediction(Predictiondto.CreatePredictionDto predictionDto) throws RuntimeException {
        Student student = su.getStudentFromRequest();
        pr.getUniquePrediction(predictionDto.matchid(), student.getId()).ifPresent(prediction -> {
            throw new RuntimeException("Prediction already exists");
        });
        pr.createPrediction(predictionDto.matchid(), predictionDto.team1score(), predictionDto.team2score(), student.getId());
    }
}
