package net.chabab.utilisateurservice.dtos;

import lombok.*;

@Data
public class LaboratoireDTO {

    private Long id;
    private String nrc;
    private String nom;
    private String logo;
    private boolean active;
    private String dateActivation;
}
