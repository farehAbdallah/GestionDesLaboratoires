package net.chabab.gestionanalyse.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestAnalyseDTO {
    private Long id;
    private String nomTest;
    private String sousEpreuve;
    private Double intervalMinDeReference;
    private Double intervalMaxDeReference;
    private String uniteDeReference;
    private String details;
    private Long fkIdAnalyse; // Reference to Analyse

}
