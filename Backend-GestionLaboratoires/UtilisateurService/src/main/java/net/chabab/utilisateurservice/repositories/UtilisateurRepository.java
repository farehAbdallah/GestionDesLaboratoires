package net.chabab.utilisateurservice.repositories;

import net.chabab.utilisateurservice.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Méthodes personnalisées si nécessaire
}
