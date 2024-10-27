package net.chabab.utilisateurservice;

import net.chabab.utilisateurservice.entites.Utilisateur;
import net.chabab.utilisateurservice.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class UtilisateurServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(UtilisateurServiceApplication.class, args);

    }

    @Bean
    CommandLineRunner commandLineRunner(UtilisateurRepository utilisateurRepository){
        return args -> {

            List utilisateurList = List.of(
                    Utilisateur.builder()
                            .role("role1")
                            .email("email1")
                            .signature("signature1")
                            .numTel("0762401781")
                            .nomComplet("nom complet")
                            .profession("profession")
                            .fkIdLaboratoire(1L)
                            .build(),
                    Utilisateur.builder()
                            .role("role2")
                            .email("email2")
                            .signature("signature2")
                            .numTel("0762401781")
                            .nomComplet("nom complet")
                            .profession("profession2")
                            .fkIdLaboratoire(1L)
                            .build()
            );

            utilisateurRepository.saveAll(utilisateurList);

        };
    }



}
