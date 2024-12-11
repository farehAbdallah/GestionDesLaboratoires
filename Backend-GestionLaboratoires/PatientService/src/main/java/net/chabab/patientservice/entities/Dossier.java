package net.chabab.patientservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.chabab.patientservice.dtos.UtilisateurDTO;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numDossier;

    @Transient
    private UtilisateurDTO utilisateur; // Utilisateur récupéré via Feign

    @Column(nullable = false)
    private String fkEmailUtilisateur; // Email de l'utilisateur gérant le dossier

    @OneToOne
    @JoinColumn(name = "fk_id_patient", nullable = false, unique = true)
    private Patient patient;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private List<Examin> examins;
}
