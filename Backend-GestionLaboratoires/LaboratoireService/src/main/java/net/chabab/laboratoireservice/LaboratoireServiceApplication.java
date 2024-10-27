package net.chabab.laboratoireservice;

import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class LaboratoireServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(LaboratoireServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(LaboratoireRepository laboratoireRepository){
		return args -> {
			Laboratoire labo1 = Laboratoire.builder()
					.nom("Micro Labo")
					.logo("C:\\labo1.png")
					.nrc("1234")
					.active(true)
					.dateActivation(LocalDate.now())
					.build();
			Laboratoire labo2 = Laboratoire.builder()
					.nom("Wild Labo")
					.logo("C:\\labo2.png")
					.nrc("12345")
					.active(false)
					.dateActivation(LocalDate.now())
					.build();
			laboratoireRepository.save(labo1);
			laboratoireRepository.save(labo2);

        };
	}

}
