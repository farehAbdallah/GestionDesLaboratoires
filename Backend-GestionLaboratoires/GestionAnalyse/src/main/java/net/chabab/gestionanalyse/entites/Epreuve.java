package net.chabab.gestionanalyse.entites;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Epreuve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomEpreuve;


    @ManyToOne
    @JoinColumn(name = "analyse_id", nullable = false)
    private Analyse analyse;
}
