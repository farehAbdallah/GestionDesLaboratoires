package net.chabab.laboratoireservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Laboratoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nrc; 

    @Column(nullable = false)
    private String nom;

    private String logo;
    private boolean active;
    private String dateActivation;

    @OneToMany(mappedBy = "laboratoire", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("laboratoire") // Empêche les boucles
    private List<ContactLaboratoire> contacts;

}
