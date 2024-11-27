package net.chabab.gestionanalyse.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TestAnalyseDTO {
    private Long id;
    private String nomTest;
    private Double valeurReference;
    private String uniteDeReference;
    private Double intervalMinDeReference;
    private Double intervalMaxDeReference;
    private String details;
}
