package me.basedatos2.pencaucu.controller.game;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.game.Gamedto;
import me.basedatos2.pencaucu.dto.responses.DataResponse;
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
    public ResponseEntity<?> createGame(@RequestBody Gamedto.CreateGameDto gamedto){
        try {
            gameService.createGame(gamedto);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(DataResponse.GenerateDataResponse("Game created"));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateScores(@RequestBody Gamedto.UpdateScoresDto teamdto){
        try{
            gameService.updateScores(teamdto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(DataResponse.GenerateDataResponse("Scores updated"));
    }
}
