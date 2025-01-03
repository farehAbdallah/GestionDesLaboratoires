package net.chabab.utilisateurservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilisateurDTO {
    private Long idUtilisateur;
    private String email;
    private String password;
    private Long fkIdLaboratoire;
    private String nomComplet;
    private String profession;
    private String numTel;
    private String signature;
    private String role;

}