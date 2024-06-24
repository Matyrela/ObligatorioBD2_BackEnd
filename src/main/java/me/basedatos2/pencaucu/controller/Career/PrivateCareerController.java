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
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Career created");
    }

    @PutMapping("delete")
    public ResponseEntity<?> deleteCareer(@RequestBody Careerdto.DeleteCareerDto careerdto){
        try {
            careerService.deleteCareer(careerdto);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Career deleted");
    }
}

