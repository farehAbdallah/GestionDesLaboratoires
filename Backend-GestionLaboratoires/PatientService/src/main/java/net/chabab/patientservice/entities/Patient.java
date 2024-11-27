package net.chabab.patientservice.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomComplet;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateNaissance;

    @Column(nullable = false)
    private String lieuDeNaissance;

    @Column(nullable = false)
    private String sexe;

    @Column(nullable = false)
    private String typePieceIdentite;

    @Column(nullable = false, unique = true)
    private String numPieceIdentite;

    private String adresse;

    private String numTel;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String visiblePour;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private Dossier dossier; // Bidirectional relationship
}
