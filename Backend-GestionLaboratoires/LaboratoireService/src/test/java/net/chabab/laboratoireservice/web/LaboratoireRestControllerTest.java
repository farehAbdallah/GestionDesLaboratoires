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

    @Test
    void testSaveAndFindById() {
        // Given
        Laboratoire laboratoire = Laboratoire.builder()
                .nom("Micro Labo")
                .logo("C:\\labo1.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.now())
                .build();

        // When
        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(laboratoire);
        when(laboratoireRepository.findById(laboratoire.getId())).thenReturn(Optional.of(laboratoire));

        Laboratoire savedLaboratoire = laboratoireRepository.save(laboratoire);
        Laboratoire foundLaboratoire = laboratoireRestController.laboratoireById(laboratoire.getId());

        // Then
        assertNotNull(savedLaboratoire);
        assertEquals("Micro Labo", foundLaboratoire.getNom());

        verify(laboratoireRepository).save(any(Laboratoire.class));
        verify(laboratoireRepository).findById(laboratoire.getId());
    }
}
