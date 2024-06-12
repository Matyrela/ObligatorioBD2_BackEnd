package me.basedatos2.pencaucu.dto.team;

public class Teamdto {
    public record  CreateTeamDto(
            Integer countryid,
            String name,
            String country
    ) {}
}
