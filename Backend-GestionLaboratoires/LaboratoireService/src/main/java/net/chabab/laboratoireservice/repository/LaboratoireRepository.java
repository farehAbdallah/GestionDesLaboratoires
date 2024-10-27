package net.chabab.laboratoireservice.repository;

import net.chabab.laboratoireservice.entities.Laboratoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface LaboratoireRepository extends JpaRepository<Laboratoire, Long> {

}
