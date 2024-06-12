package me.basedatos2.pencaucu.dto.game;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

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
            Integer scoreTeam2
    ) {}

    public record FinishGameDto(
            Integer gameid,
            Integer scoreTeam1,
            Integer scoreTeam2
    ) {}
}
