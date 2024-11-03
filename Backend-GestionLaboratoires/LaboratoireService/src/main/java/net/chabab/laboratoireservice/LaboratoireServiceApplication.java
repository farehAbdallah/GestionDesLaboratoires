package net.chabab.laboratoireservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LaboratoireServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(LaboratoireServiceApplication.class, args);
	}
	/*@Bean
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
	} */

}
