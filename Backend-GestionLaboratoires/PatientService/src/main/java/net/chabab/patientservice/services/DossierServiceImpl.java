package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.DossierDTO;
import net.chabab.patientservice.feign.UtilisateurFeignClient;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Patient;
import net.chabab.patientservice.mappers.DossierMapper;
import net.chabab.patientservice.repositories.DossierRepository;
import net.chabab.patientservice.repositories.PatientRepository;
import net.chabab.patientservice.services.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DossierServiceImpl implements DossierService {

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UtilisateurFeignClient utilisateurFeignClient;

    @Override
    public DossierDTO createDossier(DossierDTO dossierDTO) {
        // Valider que l'email utilisateur correspond au patient
        validatePatientAndEmail(dossierDTO.getPatientId(), dossierDTO.getFkEmailUtilisateur());

        // Valider que le patient existe
        Dossier dossier = DossierMapper.INSTANCE.toEntity(dossierDTO);
        dossier.setPatient(patientRepository.findById(dossierDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient avec ID " + dossierDTO.getPatientId() + " non trouvé")));

        // Sauvegarder le dossier
        Dossier savedDossier = dossierRepository.save(dossier);
        return DossierMapper.INSTANCE.toDto(savedDossier);
    }

    @Override
    public DossierDTO getDossierById(Long id) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier avec ID " + id + " non trouvé"));
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
                .orElseThrow(() -> new RuntimeException("Dossier avec ID " + id + " non trouvé"));

        // Valider que l'email utilisateur correspond au patient
        validatePatientAndEmail(dossierDTO.getPatientId(), dossierDTO.getFkEmailUtilisateur());

        dossier.setFkEmailUtilisateur(dossierDTO.getFkEmailUtilisateur());
        dossier.setDate(dossierDTO.getDate());
        dossier.setPatient(patientRepository.findById(dossierDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient avec ID " + dossierDTO.getPatientId() + " non trouvé")));

        Dossier updatedDossier = dossierRepository.save(dossier);
        return DossierMapper.INSTANCE.toDto(updatedDossier);
    }

    @Override
    public void deleteDossier(Long id) {
        if (!dossierRepository.existsById(id)) {
            throw new RuntimeException("Dossier avec ID " + id + " non trouvé");
        }
        dossierRepository.deleteById(id);
    }

    private void validatePatientAndEmail(Long patientId, String email) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient avec ID " + patientId + " non trouvé"));

        // Vérifier si l'email utilisateur correspond à l'email du patient
        if (!patient.getEmail().equals(email)) {
            throw new RuntimeException("L'email utilisateur " + email + " ne correspond pas au patient avec ID " + patientId);
        }

        // Vérifier si l'email utilisateur est valide dans UtilisateurService
        if (!utilisateurFeignClient.isEmailValid(email)) {
            throw new RuntimeException("L'email utilisateur fourni n'existe pas dans UtilisateurService : " + email);
        }
    }
}
