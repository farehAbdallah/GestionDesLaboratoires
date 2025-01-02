package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.PatientDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Examin;
import net.chabab.patientservice.entities.Patient;

import java.util.List;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO);

    PatientDTO getPatientById(Long id);

    List<PatientDTO> getAllPatients();

    PatientDTO updatePatient(Long id, PatientDTO patientDTO);

    void deletePatient(Long id);
    void sendPatientDetails(Patient patient, Dossier dossier, Examin examin);

    }
