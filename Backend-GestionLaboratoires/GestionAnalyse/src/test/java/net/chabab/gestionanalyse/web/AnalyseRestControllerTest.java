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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testSaveAndFindById() {
        Analyse analyse = Analyse.builder()
                .nom("Analyse de Sang")
                .description("Analyse pour vérifier les niveaux de glucose dans le sang.")
                .build();

        // Mock the repository behavior
        when(analyseRepository.save(any(Analyse.class))).thenReturn(analyse);
        when(analyseRepository.findById(analyse.getId())).thenReturn(Optional.of(analyse));

        // Call the controller method instead of the repository directly
        Analyse foundAnalyse = analyseRestController.analyseById(analyse.getId());

        assertNotNull(foundAnalyse);
        assertEquals("Analyse de Sang", foundAnalyse.getNom());
        assertEquals("Analyse pour vérifier les niveaux de glucose dans le sang.", foundAnalyse.getDescription());

        // Verify interactions with the mock repository
        verify(analyseRepository).findById(analyse.getId());
    }
}
