package me.basedatos2.pencaucu.dto.team;

public class Teamdto {
    public record CreateTeamDto(
            String name,
            String country
    ) {}
}