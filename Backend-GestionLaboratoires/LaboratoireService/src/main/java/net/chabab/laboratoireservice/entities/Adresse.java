package net.chabab.laboratoireservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numVoie;
    private String nomVoie;
    private String codePostal;
    private String ville;
    private String commune;

    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("adresse")
    private List<ContactLaboratoire> contacts;
}
