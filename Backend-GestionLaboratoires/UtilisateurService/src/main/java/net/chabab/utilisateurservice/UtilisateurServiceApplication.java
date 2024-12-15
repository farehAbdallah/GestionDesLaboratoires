package net.chabab.utilisateurservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "net.chabab.utilisateurservice.feign")
@SpringBootApplication
public class UtilisateurServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(UtilisateurServiceApplication.class, args);

    }

}
