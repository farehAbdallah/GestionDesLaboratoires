package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;
import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.mapper.LaboratoireMapper;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LaboratoireServiceTest {

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @InjectMocks
    private LaboratoireService laboratoireService;

    private LaboratoireDTO laboratoireDTO;
    private Laboratoire laboratoire;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation des données pour les tests
        laboratoireDTO = LaboratoireDTO.builder()
                .id(1L)
                .nom("Laboratoire A")
                .logo("logo.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.of(2023, 11, 1))
                .contacts(new ArrayList<>()) // Liste vide pour éviter NullPointerException
                .build();

        laboratoire = Laboratoire.builder()
                .id(1L)
                .nom("Laboratoire A")
                .logo("logo.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.of(2023, 11, 1))
                .contacts(new ArrayList<>()) // Liste vide
                .build();
    }

    @Test
    public void testCreateLaboratoire() {
        // Setup
        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(laboratoire);

        // Act
        LaboratoireDTO result = laboratoireService.createLaboratoire(laboratoireDTO);

        // Assert
        assertNotNull(result);
        assertEquals(laboratoireDTO.getNom(), result.getNom());
        assertEquals(LocalDate.of(2023, 11, 1), result.getDateActivation());
        verify(laboratoireRepository, times(1)).save(any(Laboratoire.class));
    }

    @Test
    public void testGetLaboratoireById() {
        // Setup
        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(laboratoire));

        // Act
        LaboratoireDTO result = laboratoireService.getLaboratoireById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(laboratoireDTO.getNom(), result.getNom());
        assertEquals(LocalDate.of(2023, 11, 1), result.getDateActivation());
        verify(laboratoireRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateLaboratoire() {
        // Setup
        LaboratoireDTO updatedLaboratoireDTO = LaboratoireDTO.builder()
                .id(1L)
                .nom("Updated Labo")
                .logo("new_logo.png")
                .nrc("5678")
                .active(true)
                .dateActivation(LocalDate.of(2024, 1, 1))
                .contacts(new ArrayList<>()) // Liste vide
                .build();

        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(laboratoire));
        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(laboratoire);

        // Act
        LaboratoireDTO result = laboratoireService.updateLaboratoire(1L, updatedLaboratoireDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Labo", result.getNom());
        assertEquals("new_logo.png", result.getLogo());
        assertEquals(LocalDate.of(2024, 1, 1), result.getDateActivation());
        verify(laboratoireRepository, times(1)).findById(1L);
        verify(laboratoireRepository, times(1)).save(any(Laboratoire.class));
    }

    @Test
    public void testDeleteLaboratoire() {
        // Setup
        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(laboratoire));

        // Act
        laboratoireService.deleteLaboratoire(1L);

        // Assert
        verify(laboratoireRepository, times(1)).findById(1L);
        verify(laboratoireRepository, times(1)).deleteById(1L);
    }

}
