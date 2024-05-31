package me.basedatos2.pencaucu.controller.game;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
@RequiredArgsConstructor
public class PublicGameController {

    private final GameService gameService;

    @GetMapping()
    public ResponseEntity<?> getGames() {
        return ResponseEntity.ok(gameService.getGames());
    }

}
