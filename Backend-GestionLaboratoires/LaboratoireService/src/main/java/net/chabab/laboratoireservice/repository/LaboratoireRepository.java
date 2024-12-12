package net.chabab.laboratoireservice.repository;

import net.chabab.laboratoireservice.entities.Laboratoire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoireRepository extends JpaRepository<Laboratoire, Long> {
}
