package net.chabab.utilisateurservice.entites;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    void testUtilisateurBuilder() {
        Utilisateur utilisateur = Utilisateur.builder()
                .email("test@example.com")
                .fkIdLaboratoire(1L)
                .nomComplet("Test User")
                .profession("Developer")
                .numTel("123456789")
                .signature("Test Signature")
                .role("USER")
                .build();

        assertEquals("test@example.com", utilisateur.getEmail());
        assertEquals(1L, utilisateur.getFkIdLaboratoire());
        assertEquals("Test User", utilisateur.getNomComplet());
    }


}
