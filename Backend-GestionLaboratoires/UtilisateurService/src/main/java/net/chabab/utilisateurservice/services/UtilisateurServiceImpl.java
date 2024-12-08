package net.chabab.utilisateurservice.services;

import net.chabab.utilisateurservice.dtos.UtilisateurDTO;
import net.chabab.utilisateurservice.entities.Utilisateur;
import net.chabab.utilisateurservice.mappers.UtilisateurMapper;
import net.chabab.utilisateurservice.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UtilisateurDTO createUtilisateur(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null) {
            throw new IllegalArgumentException("UtilisateurDTO cannot be null");
        }
        if (utilisateurDTO.getEmail() == null || utilisateurDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (utilisateurRepository.findByEmail(utilisateurDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Utilisateur utilisateur = UtilisateurMapper.INSTANCE.toEntity(utilisateurDTO);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return UtilisateurMapper.INSTANCE.toDto(savedUtilisateur);
    }

    @Override
    public UtilisateurDTO getUtilisateurById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return UtilisateurMapper.INSTANCE.toDto(utilisateur);
    }

    @Override
    public List<UtilisateurDTO> getAllUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(UtilisateurMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDTO updateUtilisateur(Long id, UtilisateurDTO utilisateurDTO) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (utilisateurDTO == null || utilisateurDTO.getEmail() == null || utilisateurDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("UtilisateurDTO or email cannot be null or empty");
        }

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setFkIdLaboratoire(utilisateurDTO.getFkIdLaboratoire());
        utilisateur.setNomComplet(utilisateurDTO.getNomComplet());
        utilisateur.setProfession(utilisateurDTO.getProfession());
        utilisateur.setNumTel(utilisateurDTO.getNumTel());
        utilisateur.setSignature(utilisateurDTO.getSignature());
        utilisateur.setRole(utilisateurDTO.getRole());

        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return UtilisateurMapper.INSTANCE.toDto(updatedUtilisateur);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return utilisateurRepository.existsByEmail(email);
    }

    @Override
    public UtilisateurDTO getUtilisateurByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + email));
        return UtilisateurMapper.INSTANCE.toDto(utilisateur);
    }
}
