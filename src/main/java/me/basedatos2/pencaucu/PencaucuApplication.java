package me.basedatos2.pencaucu;

import lombok.AllArgsConstructor;
import me.basedatos2.pencaucu.persistance.repositories.UserRespository;
import me.basedatos2.pencaucu.services.auth.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@AllArgsConstructor
public class PencaucuApplication {
	public static void main(String[] args) {
		SpringApplication.run(PencaucuApplication.class, args);
	}
}
