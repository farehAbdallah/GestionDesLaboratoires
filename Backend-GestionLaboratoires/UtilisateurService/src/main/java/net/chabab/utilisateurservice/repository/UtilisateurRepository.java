package net.chabab.utilisateurservice.repository;

import net.chabab.utilisateurservice.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
}
