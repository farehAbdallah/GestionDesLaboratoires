package net.chabab.gestionanalyse.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyseDTO {
    private Long id;
    private String nom;
    private String description;
    private List<EpreuveDTO> epreuves; // Liste des Epreuves associées

}
