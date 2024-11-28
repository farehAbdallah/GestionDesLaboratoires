package net.chabab.patientservice.dtos;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ExaminDTO {

    private Long id;
    private Long fkNumDossier; // Reference to Dossier
    private Long fkIdEpreuve;  // Reference to Epreuve
    private Long fkIdTestAnalyse; // Reference to TestAnalyse
    private String resultat; // Examination result
}
