package me.basedatos2.pencaucu.controller.Career;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Career.Careerdto;
import me.basedatos2.pencaucu.dto.responses.DataResponse;
import me.basedatos2.pencaucu.services.CareerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/career")
@RequiredArgsConstructor
public class PrivateCareerController {
    private final CareerService careerService;

    @PostMapping("")
    public ResponseEntity<?> createCareer(@RequestBody Careerdto.CreateCareerDto careerdto){
        try {
            careerService.createCareer(careerdto);
            return ResponseEntity.ok(DataResponse.GenerateDataResponse("Career created"));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(DataResponse.GenerateDataResponse(e.getMessage()));
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCareer(@PathVariable Integer id){
        Careerdto.DeleteCareerDto careerdto = new Careerdto.DeleteCareerDto(id);
        try {
            careerService.deleteCareer(careerdto);
            return ResponseEntity.ok(DataResponse.GenerateDataResponse("Career deleted"));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(DataResponse.GenerateDataResponse(e.getMessage()));
        }
    }
}

