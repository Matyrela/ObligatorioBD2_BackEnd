package me.basedatos2.pencaucu.controller.scores;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.services.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("score")
@RequiredArgsConstructor
public class PrivateScoreController {
    private final ScoreService scoreService;

    @GetMapping()
    public ResponseEntity<?> getScores() {
        return ResponseEntity.ok(scoreService.getRanking());
    }
}
