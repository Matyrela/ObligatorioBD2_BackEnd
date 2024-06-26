package me.basedatos2.pencaucu.controller.scores;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.persistance.entities.Student;
import me.basedatos2.pencaucu.services.ScoreService;
import me.basedatos2.pencaucu.util.StudentUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("score")
@RequiredArgsConstructor
public class PublicScoreController {
    private final ScoreService scoreService;
    final StudentUtils studentUtils;

    @GetMapping("/ranking")
    public ResponseEntity<?> getScores() {
        return ResponseEntity.ok(scoreService.getRanking());
    }

    @GetMapping("/self")
    public Long getLoggedStudentScore() {
        Student s = studentUtils.getStudentFromRequest();
        if (s == null) {
            return 0L;
        }
        return s.getScore();
    }
}
