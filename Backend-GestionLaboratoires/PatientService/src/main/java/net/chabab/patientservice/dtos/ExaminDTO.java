package net.chabab.patientservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminDTO {
    private Long id;
    private Long fkNumDossier; // Foreign key to Dossier
    private Long fkIdEpreuve; // Foreign key to Epreuve
    private Long fkIdTestAnalyse; // Foreign key to TestAnalyse
    private String resultat; // Result of the examination

    private EpreuveDTO epreuve; // Ajout de l'EpreuveDTO
}
