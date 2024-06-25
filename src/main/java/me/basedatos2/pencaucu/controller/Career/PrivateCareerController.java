package me.basedatos2.pencaucu.controller.Career;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Career.Careerdto;
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
            return ResponseEntity.ok("Career created");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteCareer(@PathVariable Careerdto.DeleteCareerDto careerdto){
        try {
            careerService.deleteCareer(careerdto);
            return ResponseEntity.ok("Career deleted");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}

