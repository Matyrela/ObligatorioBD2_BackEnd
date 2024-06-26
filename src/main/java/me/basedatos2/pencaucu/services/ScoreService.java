package me.basedatos2.pencaucu.services;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.score.ScoreDto;
import me.basedatos2.pencaucu.persistance.entities.Student;
import me.basedatos2.pencaucu.persistance.repositories.PredictionRepository;
import me.basedatos2.pencaucu.persistance.repositories.StudentRepository;
import me.basedatos2.pencaucu.util.StudentUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    final PredictionRepository predictionRepository;
    final StudentRepository studentRepository;
    final StudentUtils studentUtils;

    public ScoreDto.rankingResponse getRanking() {
        Student student = studentUtils.getStudentFromRequest();
        List<Student> scores = studentRepository.getGlobalRanking();

        if (student != null) {
            if (scores.size() >= 10) {
                if (!scores.contains(student)) {
                    scores.remove(9);
                    scores.add(student);
                }
            } else {
                if (!scores.contains(student)) {
                    scores.add(student);
                }
            }
        }

        return new ScoreDto.rankingResponse(scores.stream().map(s -> new ScoreDto.score(s.getId(), s.getName(), s.getLastname(), s.getScore())).toList());
    }
}
