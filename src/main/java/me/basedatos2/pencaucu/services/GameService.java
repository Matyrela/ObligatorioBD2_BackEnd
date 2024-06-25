package me.basedatos2.pencaucu.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.game.Gamedto;
import me.basedatos2.pencaucu.persistance.entities.Prediction;
import me.basedatos2.pencaucu.persistance.repositories.GameRepository;
import me.basedatos2.pencaucu.persistance.repositories.PredictionRepository;
import me.basedatos2.pencaucu.persistance.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PredictionRepository predictionRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public void createGame(Gamedto.CreateGameDto teamdto) throws RuntimeException{
        gameRepository.getUniqueGame(teamdto.date(), teamdto.time(), teamdto.team1(), teamdto.team2(), teamdto.stadium()).ifPresent(game -> {
            throw new RuntimeException("Game already exists");
        });

        gameRepository.createGame(teamdto.date(), teamdto.time(), teamdto.team1(), teamdto.team2(), teamdto.stadium());
    }

    public List<Gamedto.GameDto> getGames() {
        return gameRepository.getAllGames().stream().map(game -> new Gamedto.GameDto(
                game.getId(),
                game.getDate(),
                game.getTime(),
                game.getTeam1id().getName(),
                game.getTeam2id().getName(),
                game.getStadium(),
                game.getTeam1score(),
                game.getTeam2score()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void updateScores(Gamedto.UpdateScoresDto teamdto) throws RuntimeException {
        gameRepository.updateScores(teamdto.gameid(), teamdto.scoreTeam1(), teamdto.scoreTeam2());
        List<Prediction> affectedPredictions = predictionRepository.getAffectedPredictions(teamdto.gameid());
        for (Prediction p : affectedPredictions){
            int predictionPoints = p.getPoints();
            int points = studentRepository.getPoints(p.getStudent().getId());
            studentRepository.updatePoints(p.getStudent().getId(), points - predictionPoints);

            if (p.getTeam1score().equals(teamdto.scoreTeam1()) && p.getTeam2score().equals(teamdto.scoreTeam2())) {
                predictionRepository.updatePoints(p.getId(), 4);
                studentRepository.updatePoints(p.getStudent().getId(), points + 4);
            } else if ((p.getTeam1score() > p.getTeam2score() && teamdto.scoreTeam1() > teamdto.scoreTeam2()) ||
                    (p.getTeam1score() < p.getTeam2score() && teamdto.scoreTeam1() < teamdto.scoreTeam2()) ||
                    (p.getTeam1score().equals(p.getTeam2score()) && teamdto.scoreTeam1().equals(teamdto.scoreTeam2()))) {

                predictionRepository.updatePoints(p.getId(), 2);
                studentRepository.updatePoints(p.getStudent().getId(), points + 2);
            }else{
                predictionRepository.updatePoints(p.getId(), 0);
            }
        }
    }
}
