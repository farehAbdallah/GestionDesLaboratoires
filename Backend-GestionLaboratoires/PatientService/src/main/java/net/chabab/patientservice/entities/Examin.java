package net.chabab.patientservice.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_num_dossier", nullable = false)
    private Dossier dossier; // Foreign key to Dossier

    @Column(name = "fk_id_epreuve", nullable = false)
    private Long fkIdEpreuve; // Foreign key to Epreuve

    @Column(name = "fk_id_test_analyse", nullable = false)
    private Long fkIdTestAnalyse; // Foreign key to TestAnalyse

    @Column(nullable = false)
    private String resultat; // Result of the examination
}
