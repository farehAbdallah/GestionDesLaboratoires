package net.chabab.utilisateurservice.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Column(nullable = false, unique = true)
    private String email;

    private Long fkIdLaboratoire;

    private String nomComplet;
    private String profession;
    private String numTel;
    private String signature;
    private String role;

}