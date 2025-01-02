package net.chabab.gestionanalyse.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Analyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;
    private Long fkIdLaboratoire;

    @OneToMany(mappedBy = "analyse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Epreuve> epreuves; // Relation avec Epreuve
}
