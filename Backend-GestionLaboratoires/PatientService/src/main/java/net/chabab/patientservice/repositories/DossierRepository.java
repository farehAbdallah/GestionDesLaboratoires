package net.chabab.patientservice.repositories;

import net.chabab.patientservice.entities.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
}
