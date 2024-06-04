package me.basedatos2.pencaucu.controller.Prediction;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Prediction.Predictiondto;
import me.basedatos2.pencaucu.services.PredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("prediction")
@RequiredArgsConstructor
public class PublicPredictionController {
    private final PredictionService predictionService;

    @GetMapping()
    public ResponseEntity<?> getPredictions() {
        return ResponseEntity.ok(predictionService.getPredictions());
    }
    @PostMapping("")
    public ResponseEntity<?> createPrediction(@RequestBody Predictiondto.PredictionDto predictiondto){
        try {
            predictionService.createPrediction(predictiondto);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Prediction created");
    }

}
