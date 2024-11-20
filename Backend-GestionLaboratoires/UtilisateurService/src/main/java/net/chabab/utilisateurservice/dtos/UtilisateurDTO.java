package net.chabab.utilisateurservice.dtos;

import lombok.Data;

@Data
public class UtilisateurDTO {
    private Long idUtilisateur;
    private String email;
    private Long fkIdLaboratoire;
    private String nomComplet;
    private String profession;
    private String numTel;
    private String signature;
    private String role;
}
