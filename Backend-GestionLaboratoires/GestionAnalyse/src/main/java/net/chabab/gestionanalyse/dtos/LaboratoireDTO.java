package net.chabab.gestionanalyse.dtos;

import lombok.*;
@Builder
@Data
public class LaboratoireDTO {

    private Long id;
    private String nrc;
    private String nom;
    private String logo;
    private boolean active;
    private String dateActivation;
}
