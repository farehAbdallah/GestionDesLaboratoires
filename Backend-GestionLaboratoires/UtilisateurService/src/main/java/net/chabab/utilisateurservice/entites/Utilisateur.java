package net.chabab.utilisateurservice.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    // Déclaration des autres colonnes de la table utilisateur
    @Column(nullable = false, unique = true) // L'email doit être unique et non null
    private String email;

    private Long fkIdLaboratoire;
    private String nomComplet;
    private String profession;
    private String numTel;
    private String signature;
    private String role;
}
