package net.chabab.laboratoireservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactLaboratoireDTO {
    private Long id;
    private String numTel;
    private String fax;
    private String email;
}
