package net.chabab.gestionanalyse.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EpreuveDTO {
    private Long id;
    private String nom;
    private Long fkIdAnalyse; // ID de l'analyse associée

}
