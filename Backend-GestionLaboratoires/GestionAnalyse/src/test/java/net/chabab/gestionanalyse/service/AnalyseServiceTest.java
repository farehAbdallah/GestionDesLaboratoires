package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.entites.Analyse;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.mapper.AnalyseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyseServiceTest {

    @Mock
    private AnalyseRepository analyseRepository;

    @InjectMocks
    private AnalyseService analyseService;

    private AnalyseDTO analyseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        analyseDTO = new AnalyseDTO(1L, "Analyse A", "Description A", null);
    }

    @Test
    public void testCreateAnalyse() {
        // Setup
        Analyse analyse = AnalyseMapper.toAnalyseEntity(analyseDTO);
        when(analyseRepository.save(any(Analyse.class))).thenReturn(analyse);

        // Act
        AnalyseDTO result = analyseService.createAnalyse(analyseDTO);

        // Assert
        assertNotNull(result);
        assertEquals(analyseDTO.getNom(), result.getNom());
    }

    @Test
    public void testGetAnalyseById() {
        // Setup
        Analyse analyse = AnalyseMapper.toAnalyseEntity(analyseDTO);
        when(analyseRepository.findById(1L)).thenReturn(java.util.Optional.of(analyse));

        // Act
        AnalyseDTO result = analyseService.getAnalyseById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(analyseDTO.getNom(), result.getNom());
    }

    @Test
    public void testUpdateAnalyse() {
        // Setup
        Analyse existingAnalyse = new Analyse(1L, "Analyse A", "Old Description", null);
        AnalyseDTO analyseDTOUpdated = new AnalyseDTO(1L, "Analyse A", "Updated Description", null);
        when(analyseRepository.findById(1L)).thenReturn(java.util.Optional.of(existingAnalyse));
        when(analyseRepository.save(any(Analyse.class))).thenReturn(existingAnalyse);

        // Act
        AnalyseDTO result = analyseService.updateAnalyse(1L, analyseDTOUpdated);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    public void testDeleteAnalyse() {
        // Setup
        Analyse analyse = AnalyseMapper.toAnalyseEntity(analyseDTO);
        when(analyseRepository.findById(1L)).thenReturn(java.util.Optional.of(analyse));

        // Act
        analyseService.deleteAnalyse(1L);

        // Assert
        verify(analyseRepository, times(1)).deleteById(1L);
    }
}
