package net.chabab.patientservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numDossier; // Primary key for Dossier

    @Column(nullable = false)
    private String fkEmailUtilisateur; // References email in UtilisateurService

    @OneToOne
    @JoinColumn(name = "fk_id_patient", nullable = false, unique = true)
    private Patient patient; // Unique reference to a single Patient

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date; // Date associated with the Dossier
}
