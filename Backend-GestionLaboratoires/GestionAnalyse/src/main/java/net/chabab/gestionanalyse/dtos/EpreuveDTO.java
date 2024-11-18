package net.chabab.gestionanalyse.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EpreuveDTO {
    private Long id;
    private String nomEpreuve;  // Correspond au champ modifié dans l'entité
    private AnalyseDTO analyse;  // DTO de l'entité Analyse
}
