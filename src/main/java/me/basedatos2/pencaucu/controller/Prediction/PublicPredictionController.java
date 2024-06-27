package me.basedatos2.pencaucu.controller.Prediction;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Prediction.Predictiondto;
import me.basedatos2.pencaucu.dto.responses.DataResponse;
import me.basedatos2.pencaucu.services.PredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("prediction")
@RequiredArgsConstructor
public class PublicPredictionController {
    private final PredictionService predictionService;

    @GetMapping("/all")
    public ResponseEntity<?> getPredictions() {
        return ResponseEntity.ok(predictionService.getPredictions());
    }

    @PostMapping("")
    public ResponseEntity<?> createPrediction(@RequestBody Predictiondto.CreatePredictionDto predictiondto){
        try {
            predictionService.createPrediction(predictiondto);
            return ResponseEntity.ok(DataResponse.GenerateDataResponse("Prediction created"));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(DataResponse.GenerateDataResponse(e.getMessage()));
        }
    }
}
