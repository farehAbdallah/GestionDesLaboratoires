package net.chabab.gestionanalyse.web;

import net.chabab.gestionanalyse.entites.Analyse;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AnalyseRestControllerTest {

    @Mock
    private AnalyseRepository analyseRepository;

    @InjectMocks
    private AnalyseRestController analyseRestController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAnalyseList() {
        // Create mock data
        Analyse analyse1 = Analyse.builder()
                .id(1L)
                .nom("Analyse de Sang")
                .description("Vérifier les niveaux de glucose.")
                .build();

        Analyse analyse2 = Analyse.builder()
                .id(2L)
                .nom("Analyse d'Urine")
                .description("Vérifier la présence de protéines.")
                .build();

        List<Analyse> analyseList = Arrays.asList(analyse1, analyse2);

        // Mock the repository behavior
        when(analyseRepository.findAll()).thenReturn(analyseList);

        // Call the controller method
        List<Analyse> result = analyseRestController.analyseList();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Analyse de Sang", result.get(0).getNom());
        assertEquals("Analyse d'Urine", result.get(1).getNom());

        // Verify interactions with the mock repository
        verify(analyseRepository).findAll();
    }

    @Test
    void testAnalyseById() {
        Analyse analyse = Analyse.builder()
                .id(1L)
                .nom("Analyse de Sang")
                .description("Vérifier les niveaux de glucose dans le sang.")
                .build();

        // Mock the repository behavior
        when(analyseRepository.findById(analyse.getId())).thenReturn(Optional.of(analyse));

        // Call the controller method
        Analyse foundAnalyse = analyseRestController.analyseById(analyse.getId());

        // Assertions
        assertNotNull(foundAnalyse);
        assertEquals("Analyse de Sang", foundAnalyse.getNom());
        assertEquals("Vérifier les niveaux de glucose dans le sang.", foundAnalyse.getDescription());

        // Verify interactions with the mock repository
        verify(analyseRepository).findById(analyse.getId());
    }
}
