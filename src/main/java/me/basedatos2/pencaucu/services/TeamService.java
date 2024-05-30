package me.basedatos2.pencaucu.services;

import jakarta.transaction.Transactional;
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
    @Transactional
    public boolean createTeam(Teamdto.CreateTeamDto teamdto) {
        try {
            tr.insertTeam(teamdto.countryid(), teamdto.name(), teamdto.country());
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        return true;
    }
}
