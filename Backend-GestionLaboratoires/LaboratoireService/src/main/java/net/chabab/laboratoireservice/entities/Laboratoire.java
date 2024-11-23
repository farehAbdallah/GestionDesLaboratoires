package net.chabab.laboratoireservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Laboratoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String logo;
    private String nrc;
    private boolean active;
    private LocalDate dateActivation;

    @OneToMany(mappedBy = "laboratoire", cascade = CascadeType.ALL)
    private List<ContactLaboratoire> contacts;
}
