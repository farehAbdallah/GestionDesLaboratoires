package net.chabab.gestionanalyse.repository;

import net.chabab.gestionanalyse.entites.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyseRepository extends JpaRepository<Analyse,Long> {
}
