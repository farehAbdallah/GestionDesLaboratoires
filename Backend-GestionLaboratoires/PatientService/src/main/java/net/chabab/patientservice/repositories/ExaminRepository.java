package net.chabab.patientservice.repositories;

import net.chabab.patientservice.entities.Examin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminRepository extends JpaRepository<Examin, Long> {
}
