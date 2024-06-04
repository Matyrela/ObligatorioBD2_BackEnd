package me.basedatos2.pencaucu.services;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Prediction.Predictiondto;
import me.basedatos2.pencaucu.persistance.repositories.PredictionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PredictionService {
    private final PredictionRepository pr;

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

    public void createPrediction(Predictiondto.PredictionDto predictionDto) throws RuntimeException {
        pr.getUniquePrediction(predictionDto.matchid(), predictionDto.studentid()).ifPresent(prediction -> {
            throw new RuntimeException("Prediction already exists");
        });
        pr.createPrediction(predictionDto.matchid(), predictionDto.team1score(), predictionDto.team2score(), predictionDto.studentid());
    }
}
