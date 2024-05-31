package me.basedatos2.pencaucu.controller.team;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.team.Teamdto;
import me.basedatos2.pencaucu.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/team")
@RequiredArgsConstructor
public class PrivateTeamController {

    private final TeamService teamService;

    @PostMapping("")
    public ResponseEntity<?> createTeam(@RequestBody Teamdto.CreateTeamDto teamdto){
       try {
           teamService.createTeam(teamdto);
           return ResponseEntity.ok("Team created");
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
