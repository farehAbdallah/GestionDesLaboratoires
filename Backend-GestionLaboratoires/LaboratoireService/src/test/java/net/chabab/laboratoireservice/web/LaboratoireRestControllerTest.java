package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class LaboratoireRestControllerTest {

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @InjectMocks
    private LaboratoireRestController laboratoireRestController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test Create (Save)
    @Test
    void testCreateLaboratoire() {
        // Given
        Laboratoire laboratoire = Laboratoire.builder()
                .nom("Micro Labo")
                .logo("C:\\labo1.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.now())
                .build();

        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(laboratoire);

        // When
        Laboratoire savedLaboratoire = laboratoireRestController.createLaboratoire(laboratoire);

        // Then
        assertNotNull(savedLaboratoire);
        assertEquals("Micro Labo", savedLaboratoire.getNom());
        verify(laboratoireRepository).save(any(Laboratoire.class));
    }

    // Test Read (Find by ID)
    @Test
    void testFindLaboratoireById() {
        // Given
        Long laboratoireId = 1L;
        Laboratoire laboratoire = Laboratoire.builder()
                .id(laboratoireId)
                .nom("Micro Labo")
                .logo("C:\\labo1.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.now())
                .build();

        when(laboratoireRepository.findById(laboratoireId)).thenReturn(Optional.of(laboratoire));

        // When
        Laboratoire foundLaboratoire = laboratoireRestController.getLaboratoireById(laboratoireId).orElse(null);


        // Then
        assertNotNull(foundLaboratoire);
        assertEquals("Micro Labo", foundLaboratoire.getNom());
        verify(laboratoireRepository).findById(laboratoireId);
    }

    // Test Update
    @Test
    void testUpdateLaboratoire() {
        // Given
        Long laboratoireId = 1L;
        Laboratoire existingLaboratoire = Laboratoire.builder()
                .id(laboratoireId)
                .nom("Old Labo")
                .logo("C:\\old_labo.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.now())
                .build();

        Laboratoire updatedLaboratoire = Laboratoire.builder()
                .id(laboratoireId)
                .nom("Updated Labo")
                .logo("C:\\updated_labo.png")
                .nrc("5678")
                .active(true)
                .dateActivation(LocalDate.now())
                .build();

        when(laboratoireRepository.findById(laboratoireId)).thenReturn(Optional.of(existingLaboratoire));
        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(updatedLaboratoire);

        // When
        Laboratoire result = laboratoireRestController.updateLaboratoire(laboratoireId, updatedLaboratoire);

        // Then
        assertNotNull(result);
        assertEquals("Updated Labo", result.getNom());
        assertEquals("C:\\updated_labo.png", result.getLogo());
        verify(laboratoireRepository).findById(laboratoireId);
        verify(laboratoireRepository).save(any(Laboratoire.class));
    }

    // Test Delete
    @Test
    void testDeleteLaboratoire() {
        // Given
        Long laboratoireId = 1L;
        Laboratoire laboratoire = Laboratoire.builder()
                .id(laboratoireId)
                .nom("Micro Labo")
                .logo("C:\\labo1.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.now())
                .build();

        when(laboratoireRepository.findById(laboratoireId)).thenReturn(Optional.of(laboratoire));

        // When
        laboratoireRestController.deleteLaboratoire(laboratoireId);

        // Then
        verify(laboratoireRepository).findById(laboratoireId);
        verify(laboratoireRepository).delete(laboratoire);
    }
}
