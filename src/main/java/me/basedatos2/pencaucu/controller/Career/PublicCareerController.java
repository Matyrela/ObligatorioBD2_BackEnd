package me.basedatos2.pencaucu.controller.Career;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.services.CareerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("career")
@RequiredArgsConstructor
public class PublicCareerController {
    private final CareerService careerService;

    @GetMapping("")
    public ResponseEntity<?> getCareers(){
        return ResponseEntity.ok(careerService.getCareers());
    }



}
