package net.chabab.patientservice.services.impl;

import net.chabab.patientservice.dtos.DossierDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.mappers.DossierMapper;
import net.chabab.patientservice.repositories.DossierRepository;
import net.chabab.patientservice.repositories.PatientRepository;
import net.chabab.patientservice.services.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DossierServiceImpl implements DossierService {

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String utilisateurServiceUrl = "http://localhost:8081/api/utilisateurs/validate-email";

    @Override
    public DossierDTO createDossier(DossierDTO dossierDTO) {
        // Validate Patient
        Dossier dossier = DossierMapper.INSTANCE.toEntity(dossierDTO);
        dossier.setPatient(patientRepository.findById(dossierDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient with ID " + dossierDTO.getPatientId() + " not found")));

        // Validate Utilisateur Email
        if (!isEmailValid(dossierDTO.getFkEmailUtilisateur())) {
            throw new RuntimeException("Invalid utilisateur email: " + dossierDTO.getFkEmailUtilisateur());
        }

        // Save and Return
        Dossier savedDossier = dossierRepository.save(dossier);
        return DossierMapper.INSTANCE.toDto(savedDossier);
    }

    private boolean isEmailValid(String email) {
        String url = utilisateurServiceUrl + "?email=" + email;
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (Exception e) {
            throw new RuntimeException("Error validating email: " + email, e);
        }
    }

    @Override
    public DossierDTO getDossierById(Long id) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier with ID " + id + " not found"));
        return DossierMapper.INSTANCE.toDto(dossier);
    }

    @Override
    public List<DossierDTO> getAllDossiers() {
        return dossierRepository.findAll()
                .stream()
                .map(DossierMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DossierDTO updateDossier(Long id, DossierDTO dossierDTO) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier with ID " + id + " not found"));

        dossier.setPatient(patientRepository.findById(dossierDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient with ID " + dossierDTO.getPatientId() + " not found")));

        if (!isEmailValid(dossierDTO.getFkEmailUtilisateur())) {
            throw new RuntimeException("Invalid utilisateur email: " + dossierDTO.getFkEmailUtilisateur());
        }

        dossier.setFkEmailUtilisateur(dossierDTO.getFkEmailUtilisateur());
        dossier.setDate(dossierDTO.getDate());

        Dossier updatedDossier = dossierRepository.save(dossier);
        return DossierMapper.INSTANCE.toDto(updatedDossier);
    }

    @Override
    public void deleteDossier(Long id) {
        if (!dossierRepository.existsById(id)) {
            throw new RuntimeException("Dossier with ID " + id + " not found");
        }
        dossierRepository.deleteById(id);
    }
}
