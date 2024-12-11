package net.chabab.utilisateurservice.services;

import net.chabab.utilisateurservice.dtos.UtilisateurDTO;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDTO createUtilisateur(UtilisateurDTO utilisateurDTO);
    UtilisateurDTO getUtilisateurById(Long id);
    List<UtilisateurDTO> getAllUtilisateurs();
    UtilisateurDTO updateUtilisateur(Long id, UtilisateurDTO utilisateurDTO);
    void deleteUtilisateur(Long id);
    boolean isEmailValid(String email);
    UtilisateurDTO getUtilisateurByEmail(String email);

}