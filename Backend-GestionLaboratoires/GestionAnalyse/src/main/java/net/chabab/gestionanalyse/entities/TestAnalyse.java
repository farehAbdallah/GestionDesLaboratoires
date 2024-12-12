package net.chabab.gestionanalyse.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class TestAnalyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomTest;

    private String sousEpreuve;

    private Double intervalMinDeReference;

    private Double intervalMaxDeReference;

    private String uniteDeReference;

    private String details;

    @ManyToOne
    @JoinColumn(name = "fkIdAnalyse", nullable = false)
    private Analyse analyse;
}
