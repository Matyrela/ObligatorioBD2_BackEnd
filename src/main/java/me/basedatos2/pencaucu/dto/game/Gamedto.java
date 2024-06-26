package me.basedatos2.pencaucu.dto.game;

import lombok.Builder;
import me.basedatos2.pencaucu.dto.Prediction.Predictiondto;
import me.basedatos2.pencaucu.persistance.entities.Prediction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Gamedto {
    public record CreateGameDto(
            LocalDate date,
            LocalTime time,
            Integer team1,
            Integer team2,
            String stadium
    ) {}

    @Builder
    public record UpdateScoresDto(
            Integer gameid,
            Integer scoreTeam1,
            Integer scoreTeam2
    ) {}

    public record GameDto(
            Integer gameid,
            LocalDate date,
            LocalTime time,
            String team1,
            String team2,
            String stadium,
            Integer scoreTeam1,
            Integer scoreTeam2,
            Predictiondto.PredictionDto myPrediction
    ) {}

    public record FinishGameDto(
            Integer scoreTeam1,
            Integer scoreTeam2
    ) {}

    public record GameTypesDto(
            List<GameDto> futureGames,
            List<GameDto> inProgressGames,
            List<GameDto> pastGames
    ) {

    }
}
