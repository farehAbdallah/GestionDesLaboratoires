package net.chabab.patientservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DossierDTO {
    private Long numDossier; // Primary key for Dossier
    private String fkEmailUtilisateur; // Email from UtilisateurService
    private Long patientId; // References Patient's primary key
    private Date date; // Date associated with the Dossier
}
