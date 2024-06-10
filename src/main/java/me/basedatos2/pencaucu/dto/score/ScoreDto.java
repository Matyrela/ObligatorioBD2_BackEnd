package me.basedatos2.pencaucu.dto.score;

import java.util.List;

public class ScoreDto {

    public record rankingResponse(
            List<score> scores
    ) {
    }

    public record score(
            Long ci,
            String name,
            String lastname,
            Long score
    ) {
    }
}
