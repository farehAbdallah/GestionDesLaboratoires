package net.chabab.gestionanalyse.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class TestAnalyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomTest;

    private Double valeurReference;

    private String uniteDeReference;

    private Double intervalMinDeReference;

    private Double intervalMaxDeReference;

    private String details;
}
