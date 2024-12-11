package net.chabab.gestionanalyse.repository;

import net.chabab.gestionanalyse.entities.TestAnalyse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestAnalyseRepository extends JpaRepository<TestAnalyse, Long> {
    List<TestAnalyse> findByAnalyse_Id(Long fkIdAnalyse);
}
