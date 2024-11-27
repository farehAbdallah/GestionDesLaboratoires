package net.chabab.patientservice.repositories;

import net.chabab.patientservice.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    boolean existsByNumPieceIdentite(String numPieceIdentite);
}
