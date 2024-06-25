package me.basedatos2.pencaucu.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.dto.Career.Careerdto;
import me.basedatos2.pencaucu.persistance.repositories.CareerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;

    public List<Careerdto.CareerDto> getCareers() {
        return careerRepository.getCareers().stream().map(career -> new Careerdto.CareerDto(
                career.getId(),
                career.getName()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void createCareer(Careerdto.CreateCareerDto careerdto){
        if (careerRepository.getUniqueCareer(careerdto.name().toLowerCase()).isPresent()) {
            throw new RuntimeException("Career already exists");
        }
        careerRepository.createCareer(careerdto.name());
    }

    @Transactional
    public void deleteCareer(Careerdto.DeleteCareerDto careerdto) {
        careerRepository.deleteCareer(careerdto.id());
    }
}
