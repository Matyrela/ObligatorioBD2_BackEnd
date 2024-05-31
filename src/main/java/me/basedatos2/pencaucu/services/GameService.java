package me.basedatos2.pencaucu.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.game.Gamedto;
import me.basedatos2.pencaucu.persistance.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    @Transactional
    public void createGame(Gamedto.CreateGameDto teamdto) throws RuntimeException{
        gameRepository.getUniqueGame(teamdto.date(), teamdto.time(), teamdto.team1(), teamdto.team2(), teamdto.stadium()).ifPresent(game -> {
            throw new RuntimeException("Game already exists");
        });

        gameRepository.createGame(teamdto.date(), teamdto.time(), teamdto.team1(), teamdto.team2(), teamdto.stadium());
    }

    public void updateScores(Gamedto.UpdateScoresDto teamdto) {

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
}
