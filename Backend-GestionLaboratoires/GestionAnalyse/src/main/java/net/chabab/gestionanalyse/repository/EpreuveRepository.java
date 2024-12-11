package net.chabab.gestionanalyse.repository;

import net.chabab.gestionanalyse.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {
    List<Epreuve> findByAnalyseId(Long analyseId); // Recherche par Analyse
}
