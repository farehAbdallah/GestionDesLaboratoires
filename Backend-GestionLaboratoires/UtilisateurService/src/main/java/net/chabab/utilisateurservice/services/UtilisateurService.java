package net.chabab.utilisateurservice.services;

import net.chabab.utilisateurservice.entites.Utilisateur;
import net.chabab.utilisateurservice.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Créer un nouvel utilisateur
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    // Récupérer tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Récupérer un utilisateur par ID
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé avec l'ID : " + id));
    }

    // Mettre à jour un utilisateur existant
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = getUtilisateurById(id);  // Vérifie l'existence de l'utilisateur
        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setNomComplet(utilisateurDetails.getNomComplet());
        utilisateur.setProfession(utilisateurDetails.getProfession());
        utilisateur.setNumTel(utilisateurDetails.getNumTel());
        utilisateur.setRole(utilisateurDetails.getRole());
        utilisateur.setFkIdLaboratoire(utilisateurDetails.getFkIdLaboratoire());
        utilisateur.setSignature(utilisateurDetails.getSignature());
        return utilisateurRepository.save(utilisateur);
    }

    // Supprimer un utilisateur
    public void deleteUtilisateur(Long id) {
        Utilisateur utilisateur = getUtilisateurById(id);  // Vérifie l'existence de l'utilisateur
        utilisateurRepository.delete(utilisateur);
    }
}
