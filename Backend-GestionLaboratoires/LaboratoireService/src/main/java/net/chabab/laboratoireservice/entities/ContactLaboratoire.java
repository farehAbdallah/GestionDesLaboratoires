package net.chabab.laboratoireservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ContactLaboratoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numTel;
    private String fax;
    private String email;

    @ManyToOne
    @JoinColumn(name = "laboratoire_id")
    @JsonIgnoreProperties("contacts") // Ignorer la liste de contacts pour éviter des boucles
    private Laboratoire laboratoire;

    @ManyToOne
    @JoinColumn(name = "adresse_id")
    @JsonIgnoreProperties("contacts") // Ignorer la liste de contacts pour éviter des boucles
    private Adresse adresse;

}
