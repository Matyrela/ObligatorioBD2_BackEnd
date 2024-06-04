package me.basedatos2.pencaucu.dto.Prediction;

public class Predictiondto {
    public record PredictionDto(
            Integer id,
            Integer matchid,
            Integer points,
            Integer team1score,
            Integer team2score,
            java.math.BigDecimal studentid
    ) {}
}
