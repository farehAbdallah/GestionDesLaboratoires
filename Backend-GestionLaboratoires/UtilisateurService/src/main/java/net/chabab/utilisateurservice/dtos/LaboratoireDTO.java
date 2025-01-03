package net.chabab.utilisateurservice.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LaboratoireDTO {
    private Long id;
    private String nom;
    private String logo;
    private String nrc;
    private boolean active;
    private String dateActivation;
}
