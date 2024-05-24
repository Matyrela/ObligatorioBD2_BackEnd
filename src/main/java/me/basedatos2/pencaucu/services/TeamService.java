package me.basedatos2.pencaucu.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.team.Teamdto;
import me.basedatos2.pencaucu.persistance.entities.Team;
import me.basedatos2.pencaucu.persistance.repositories.TeamRepository;
import org.springframework.stereotype.Service;


import java.util.List;
@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository tr;
    public List<Team> getTeams() {

        return tr.getTeams();
    }

    public void createTeam(Teamdto.CreateTeamDto teamdto) {
        if(!tr.getTeamsByCountryAndName(teamdto.country(), teamdto.name())){
            throw new IllegalArgumentException("El equipo ya existe");
        }
        tr.insertTeam(teamdto.name(), teamdto.country());
    }
}