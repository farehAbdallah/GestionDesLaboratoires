package net.chabab.patientservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpreuveDTO {
    private Long id;               // ID de l'épreuve
    private String nom;            // Nom de l'épreuve
    private Long fkIdAnalyse;      // ID de l'analyse associée
}
