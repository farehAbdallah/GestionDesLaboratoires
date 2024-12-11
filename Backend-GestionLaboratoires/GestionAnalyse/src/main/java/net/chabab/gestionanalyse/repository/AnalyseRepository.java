package net.chabab.gestionanalyse.repository;

import net.chabab.gestionanalyse.entities.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyseRepository extends JpaRepository<Analyse, Long> {
}
