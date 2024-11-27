package net.chabab.patientservice.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PatientDTO {
    private Long id;
    private String nomComplet;
    private Date dateNaissance;
    private String lieuDeNaissance;
    private String sexe;
    private String typePieceIdentite;
    private String numPieceIdentite;
    private String adresse;
    private String numTel;
    private String email;
    private String visiblePour;
}
