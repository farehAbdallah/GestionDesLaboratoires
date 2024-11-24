package net.chabab.utilisateurservice.services.impl;

import net.chabab.utilisateurservice.dtos.UtilisateurDTO;
import net.chabab.utilisateurservice.entities.Utilisateur;
import net.chabab.utilisateurservice.repositories.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import net.chabab.utilisateurservice.services.impl.UtilisateurServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilisateurServiceImplTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurServiceImpl utilisateurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUtilisateur() {
        // Arrange
        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email("test@example.com")
                .nomComplet("John Doe")
                .build();
        Utilisateur utilisateur = Utilisateur.builder()
                .idUtilisateur(1L)
                .email("test@example.com")
                .nomComplet("John Doe")
                .build();

        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        // Act
        UtilisateurDTO result = utilisateurService.createUtilisateur(utilisateurDTO);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
    }

    @Test
    void testGetUtilisateurById() {
        // Arrange
        Utilisateur utilisateur = Utilisateur.builder()
                .idUtilisateur(1L)
                .email("test@example.com")
                .nomComplet("John Doe")
                .build();

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        // Act
        UtilisateurDTO result = utilisateurService.getUtilisateurById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(utilisateurRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUtilisateur() {
        // Arrange
        when(utilisateurRepository.existsById(1L)).thenReturn(true);

        // Act
        utilisateurService.deleteUtilisateur(1L);

        // Assert
        verify(utilisateurRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetUtilisateurById_NonExistentId() {
        // Arrange
        when(utilisateurRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> utilisateurService.getUtilisateurById(999L));
        assertEquals("Utilisateur non trouvé", exception.getMessage());
        verify(utilisateurRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateUtilisateur_NullFields() {
        // Arrange
        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email(null) // Invalid field
                .nomComplet("John Doe")
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> utilisateurService.createUtilisateur(utilisateurDTO));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDeleteUtilisateur_NonExistentId() {
        // Arrange
        when(utilisateurRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> utilisateurService.deleteUtilisateur(999L));
        assertEquals("Utilisateur non trouvé", exception.getMessage());
        verify(utilisateurRepository, times(1)).existsById(999L);
        verify(utilisateurRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateUtilisateur_InvalidData() {
        // Arrange
        Utilisateur utilisateur = Utilisateur.builder()
                .idUtilisateur(1L)
                .email("valid@example.com")
                .nomComplet("John Doe")
                .build();
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email("") // Invalid email
                .nomComplet("Updated Name")
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> utilisateurService.updateUtilisateur(1L, utilisateurDTO));
        assertEquals("UtilisateurDTO or email cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDeleteUtilisateur_InvalidId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> utilisateurService.deleteUtilisateur(0L));
        assertEquals("Invalid ID", exception.getMessage());
    }

    @Test
    void testGetUtilisateurById_NullId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> utilisateurService.getUtilisateurById(null));
        assertEquals("ID cannot be null", exception.getMessage());
    }

    @Test
    void testCreateUtilisateur_DuplicateEmail() {
        // Arrange
        Utilisateur utilisateur = Utilisateur.builder()
                .idUtilisateur(1L)
                .email("duplicate@example.com")
                .nomComplet("John Doe")
                .build();
        when(utilisateurRepository.findByEmail("duplicate@example.com")).thenReturn(Optional.of(utilisateur));

        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email("duplicate@example.com") // Duplicate email
                .nomComplet("New User")
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> utilisateurService.createUtilisateur(utilisateurDTO));
        assertEquals("Email already exists", exception.getMessage());
        verify(utilisateurRepository, never()).save(any(Utilisateur.class));
    }

    @Test
    void testUpdateUtilisateur_NonExistentId() {
        // Arrange
        when(utilisateurRepository.findById(999L)).thenReturn(Optional.empty());

        UtilisateurDTO utilisateurDTO = UtilisateurDTO.builder()
                .email("updated@example.com")
                .nomComplet("Updated Name")
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> utilisateurService.updateUtilisateur(999L, utilisateurDTO));
        assertEquals("Utilisateur non trouvé", exception.getMessage());
        verify(utilisateurRepository, never()).save(any(Utilisateur.class));
    }

    @Test
    void testCreateUtilisateur_NullInput() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> utilisateurService.createUtilisateur(null));
        assertEquals("UtilisateurDTO cannot be null", exception.getMessage());
    }

    @Test
    void testGetAllUtilisateurs_EmptyDatabase() {
        // Arrange
        when(utilisateurRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<UtilisateurDTO> result = utilisateurService.getAllUtilisateurs();

        // Assert
        assertTrue(result.isEmpty());
        verify(utilisateurRepository, times(1)).findAll();
    }


}
