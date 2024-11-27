package net.chabab.laboratoireservice.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoireDTO {
    private Long id;
    private String nom;
    private String logo;
    private String nrc;
    private boolean active;
    private LocalDate dateActivation;
    private List<ContactLaboratoireDTO> contacts;
}
