package net.chabab.utilisateurservice.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
@Entity
public class Utilisateur {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;
    private String email;
    private Long fkIdLaboratoire;
    private String nomComplet;
    private String profession;
    private String numTel;
    private String signature;
    private String role;
}
