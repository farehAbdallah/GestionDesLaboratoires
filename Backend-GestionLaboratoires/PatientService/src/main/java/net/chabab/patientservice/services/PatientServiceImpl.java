package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.PatientDTO;
//import net.chabab.patientservice.feign.UtilisateurFeignClient;
import net.chabab.patientservice.entities.Patient;
import net.chabab.patientservice.mappers.PatientMapper;
import net.chabab.patientservice.repositories.PatientRepository;
import net.chabab.patientservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

//    @Autowired
//    private UtilisateurFeignClient utilisateurFeignClient;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
//        // Valider l'email utilisateur avant d'ajouter un patient
//        if (!utilisateurFeignClient.isEmailValid(patientDTO.getEmail())) {
//            throw new RuntimeException("L'email utilisateur fourni n'existe pas : " + patientDTO.getEmail());
//        }

        // Ajouter le patient
        Patient patient = PatientMapper.INSTANCE.toEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.INSTANCE.toDto(savedPatient);
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID : " + id));
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
        // Valider l'email utilisateur avant de mettre à jour un patient
//        if (!utilisateurFeignClient.isEmailValid(patientDTO.getEmail())) {
//            throw new RuntimeException("L'email utilisateur fourni n'existe pas : " + patientDTO.getEmail());
//        }

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID : " + id));

        // Mettre à jour les champs du patient
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
            throw new RuntimeException("Patient non trouvé avec l'ID : " + id);
        }
        patientRepository.deleteById(id);
    }
}
