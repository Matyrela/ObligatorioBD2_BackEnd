package me.basedatos2.pencaucu.dto.Career;

public class Careerdto {
    public record CareerDto(
            Integer id,
            String name
    ) {}
    public record CreateCareerDto(
            String name
    ) {}
    public record DeleteCareerDto(
            Integer id
    ) {}
}
