package net.chabab.laboratoireservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LaboratoireServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LaboratoireServiceApplication.class, args);
	}
}
