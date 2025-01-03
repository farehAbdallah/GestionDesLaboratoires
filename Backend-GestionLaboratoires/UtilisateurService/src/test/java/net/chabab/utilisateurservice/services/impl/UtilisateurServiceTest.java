package net.chabab.utilisateurservice.services.impl;

import net.chabab.utilisateurservice.dtos.LaboratoireDTO;
import net.chabab.utilisateurservice.dtos.UtilisateurDTO;
import net.chabab.utilisateurservice.entities.Utilisateur;
import net.chabab.utilisateurservice.feign.LaboratoireClient;
import net.chabab.utilisateurservice.mappers.UtilisateurMapper;
import net.chabab.utilisateurservice.repositories.UtilisateurRepository;
import net.chabab.utilisateurservice.services.UtilisateurServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private LaboratoireClient laboratoireClient;

    @InjectMocks
    private UtilisateurServiceImpl utilisateurService;

    @Test
    void testCreateUtilisateur_Success() {
        // Données de test
        LaboratoireDTO laboratoire = LaboratoireDTO.builder()
                .id(1L)
                .nom("Laboratoire Test")
                .build();

        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email("test@example.com")
                .password("password")
                .fkIdLaboratoire(1L)
                .nomComplet("Test User")
                .role("USER")
                .build();

        Utilisateur utilisateur = UtilisateurMapper.INSTANCE.toEntity(utilisateurDTO);

        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(laboratoire);
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Exécution
        UtilisateurDTO result = utilisateurService.createUtilisateur(utilisateurDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(utilisateurRepository, times(1)).findByEmail("test@example.com");
        verify(laboratoireClient, times(1)).getLaboratoireById(1L);
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
    }

    @Test
    void testCreateUtilisateur_EmailExists() {
        // Données de test
        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new Utilisateur()));

        // Exécution & Vérification
        assertThrows(RuntimeException.class, () -> utilisateurService.createUtilisateur(utilisateurDTO));
        verify(utilisateurRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetUtilisateurById_Success() {
        // Données de test
        Utilisateur utilisateur = Utilisateur.builder()
                .idUtilisateur(1L)
                .email("test@example.com")
                .build();

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        // Exécution
        UtilisateurDTO result = utilisateurService.getUtilisateurById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getIdUtilisateur());
        verify(utilisateurRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUtilisateurById_NotFound() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());

        // Exécution & Vérification
        assertThrows(RuntimeException.class, () -> utilisateurService.getUtilisateurById(1L));
        verify(utilisateurRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUtilisateur_Success() {
        // Données de test
        LaboratoireDTO laboratoire = LaboratoireDTO.builder()
                .id(1L)
                .nom("Laboratoire Test")
                .build();

        Utilisateur utilisateur = Utilisateur.builder()
                .idUtilisateur(1L)
                .email("test@example.com")
                .build();

        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email("updated@example.com")
                .fkIdLaboratoire(1L)
                .build();

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(laboratoire);
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Exécution
        UtilisateurDTO result = utilisateurService.updateUtilisateur(1L, utilisateurDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        verify(utilisateurRepository, times(1)).findById(1L);
        verify(laboratoireClient, times(1)).getLaboratoireById(1L);
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
    }

    @Test
    void testDeleteUtilisateur_Success() {
        when(utilisateurRepository.existsById(1L)).thenReturn(true);

        // Exécution
        utilisateurService.deleteUtilisateur(1L);

        // Vérifications
        verify(utilisateurRepository, times(1)).existsById(1L);
        verify(utilisateurRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUtilisateur_NotFound() {
        when(utilisateurRepository.existsById(1L)).thenReturn(false);

        // Exécution & Vérification
        assertThrows(RuntimeException.class, () -> utilisateurService.deleteUtilisateur(1L));
        verify(utilisateurRepository, times(1)).existsById(1L);
    }

    @Test
    void testGetUtilisateurByEmail_Success() {
        // Données de test
        Utilisateur utilisateur = Utilisateur.builder()
                .email("test@example.com")
                .build();

        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        // Exécution
        UtilisateurDTO result = utilisateurService.getUtilisateurByEmail("test@example.com");

        // Vérifications
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(utilisateurRepository, times(1)).findByEmail("test@example.com");
    }
}
