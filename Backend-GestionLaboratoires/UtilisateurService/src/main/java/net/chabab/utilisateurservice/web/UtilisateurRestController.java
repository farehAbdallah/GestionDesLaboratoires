package net.chabab.utilisateurservice.web;

import net.chabab.utilisateurservice.entites.Utilisateur;
import net.chabab.utilisateurservice.repository.UtilisateurRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurRestController {
    private UtilisateurRepository utilisateurRepository;
    public UtilisateurRestController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    @GetMapping("/utilisateurs")
    public List<Utilisateur> utilisateurList(){
        return utilisateurRepository.findAll();
    }

    @GetMapping("/utilisateurs/{id}")
    public Utilisateur utilisateurById(@PathVariable Long id){
        return utilisateurRepository.findById(id).get();
    }
}
