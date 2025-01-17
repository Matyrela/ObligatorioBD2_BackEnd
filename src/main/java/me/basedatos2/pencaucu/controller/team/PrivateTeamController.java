package me.basedatos2.pencaucu.controller.team;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.responses.DataResponse;
import me.basedatos2.pencaucu.dto.team.Teamdto;
import me.basedatos2.pencaucu.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/team")
@RequiredArgsConstructor
public class PrivateTeamController {

    private final TeamService teamService;

    @PostMapping("")
    public ResponseEntity<?> createTeam(@RequestBody Teamdto.CreateTeamDto teamdto){
       try {
           teamService.createTeam(teamdto);
           return ResponseEntity.ok(DataResponse.GenerateDataResponse("Team created"));
       }catch (Exception e){
           return ResponseEntity.badRequest().body(DataResponse.GenerateDataResponse(e.getMessage()));
       }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer id){
        Teamdto.DeleteTeamDto deleteTeamDto = new Teamdto.DeleteTeamDto(id);
        try {
            teamService.deleteTeam(deleteTeamDto);
            return ResponseEntity.ok(DataResponse.GenerateDataResponse("Team deleted"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(DataResponse.GenerateDataResponse(e.getMessage()));
        }
    }
}
