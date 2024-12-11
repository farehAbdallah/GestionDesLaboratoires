package net.chabab.gestionanalyse.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AnalyseDTO {
    private Long id;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Description is required")
    private String description;

    private Long fkIdLaboratoire;

    private List<EpreuveDTO> epreuves;
}
