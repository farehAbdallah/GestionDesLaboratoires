package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.PatientDTO;
import net.chabab.patientservice.entities.Patient;
import net.chabab.patientservice.mappers.PatientMapper;
import net.chabab.patientservice.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        if (patientDTO == null) {
            throw new IllegalArgumentException("PatientDTO cannot be null");
        }
        if (patientDTO.getNumPieceIdentite() == null || patientRepository.existsByNumPieceIdentite(patientDTO.getNumPieceIdentite())) {
            throw new RuntimeException("Identity document number already exists");
        }
        Patient patient = PatientMapper.INSTANCE.toEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.INSTANCE.toDto(savedPatient);
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return PatientMapper.INSTANCE.toDto(patient);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(PatientMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setNomComplet(patientDTO.getNomComplet());
        patient.setDateNaissance(patientDTO.getDateNaissance());
        patient.setLieuDeNaissance(patientDTO.getLieuDeNaissance());
        patient.setSexe(patientDTO.getSexe());
        patient.setTypePieceIdentite(patientDTO.getTypePieceIdentite());
        patient.setNumPieceIdentite(patientDTO.getNumPieceIdentite());
        patient.setAdresse(patientDTO.getAdresse());
        patient.setNumTel(patientDTO.getNumTel());
        patient.setEmail(patientDTO.getEmail());
        patient.setVisiblePour(patientDTO.getVisiblePour());

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.INSTANCE.toDto(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found");
        }
        patientRepository.deleteById(id);
    }
}
