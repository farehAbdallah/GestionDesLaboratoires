package net.chabab.utilisateurservice.web;

import net.chabab.utilisateurservice.entites.Utilisateur;
import net.chabab.utilisateurservice.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@DataJpaTest
@SpringBootTest
@ActiveProfiles("test")
class UtilisateurRestControllerTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurRestController utilisateurRestController;

    public UtilisateurRestControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAndFinfById() {
        Utilisateur utilisateur = Utilisateur.builder()
                .email("user@example.com")
                .nomComplet("User Example")
                .profession("Tester")
                .build();

        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);
        when(utilisateurRepository.findById(utilisateur.getIdUtilisateur())).thenReturn(java.util.Optional.of(utilisateur));


        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        Utilisateur foundUtilisateur = utilisateurRestController.utilisateurById(utilisateur.getIdUtilisateur());

        assertNotNull(savedUtilisateur);
        assertEquals("user@example.com", foundUtilisateur.getEmail());

        verify(utilisateurRepository).save(any(Utilisateur.class));
        verify(utilisateurRepository).findById(utilisateur.getIdUtilisateur());


         }
}
