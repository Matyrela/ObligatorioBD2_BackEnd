package me.basedatos2.pencaucu.controller.game;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.game.Gamedto;
import me.basedatos2.pencaucu.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/game")
@RequiredArgsConstructor
public class PrivateGameController {

    private final GameService gameService;

    @PostMapping("")
    public ResponseEntity<?> createTeam(@RequestBody Gamedto.CreateGameDto teamdto){
        try {
            gameService.createGame(teamdto);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Game created");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateScores(@RequestBody Gamedto.UpdateScoresDto teamdto){
        gameService.updateScores(teamdto);
        return ResponseEntity.ok("Scores updated");
    }

}
