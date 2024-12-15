package net.chabab.laboratoireservice.dtos;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LaboratoireDTO {

    private Long id;
    private String nrc;
    private String nom;
    private String logo;
    private boolean active;
    private String dateActivation;
    private List<ContactLaboratoireDTO> contacts;
}
