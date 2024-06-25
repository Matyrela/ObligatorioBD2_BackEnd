package me.basedatos2.pencaucu.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.team.Teamdto;
import me.basedatos2.pencaucu.persistance.entities.Team;
import me.basedatos2.pencaucu.persistance.repositories.TeamRepository;
import org.springframework.stereotype.Service;


import java.util.List;
@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public List<Team> getTeams() {
        return teamRepository.getTeams();
    }

    @Transactional
    public boolean createTeam(Teamdto.CreateTeamDto teamdto) throws Exception {
        teamRepository.getTeamsById(teamdto.countryid()).ifPresent(team -> {
            throw new RuntimeException("Equipo ya agregado");
        });

        System.out.println(teamdto.countryid());

        teamRepository.insertTeam(teamdto.countryid(), teamdto.name(), teamdto.country());
        return true;
    }

    @Transactional
    public void deleteTeam(Teamdto.DeleteTeamDto deleteTeamDto) {
        teamRepository.deleteTeam(deleteTeamDto.id());
    }
}
