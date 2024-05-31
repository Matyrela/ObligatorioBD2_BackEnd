package me.basedatos2.pencaucu.controller.team;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("team")
@RequiredArgsConstructor
public class PublicTeamController {
    private final TeamService ts;

    @GetMapping("")
    public ResponseEntity<?> getTeams(){
        return ResponseEntity.ok(ts.getTeams());
    }


}
