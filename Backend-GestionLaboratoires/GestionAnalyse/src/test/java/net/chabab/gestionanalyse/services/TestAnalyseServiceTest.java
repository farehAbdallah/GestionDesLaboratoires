package net.chabab.gestionanalyse.services;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.entities.TestAnalyse;
import net.chabab.gestionanalyse.mapper.TestAnalyseMapper;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.repository.TestAnalyseRepository;
import net.chabab.gestionanalyse.service.TestAnalyseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestAnalyseServiceTest {

    @Mock
    private TestAnalyseRepository testAnalyseRepository;

    @Mock
    private AnalyseRepository analyseRepository;

    @InjectMocks
    private TestAnalyseServiceImpl testAnalyseService;

    @Test
    void testCreateTestAnalyse() {
        // Données de test
        Analyse analyse = Analyse.builder().id(1L).nom("Analyse Test").build();

        TestAnalyseDTO testAnalyseDTO = TestAnalyseDTO.builder()
                .nomTest("Test Analyse 1")
                .sousEpreuve("Sous-épreuve 1")
                .intervalMinDeReference(10.0)
                .intervalMaxDeReference(20.0)
                .uniteDeReference("mg/L")
                .details("Détails du test")
                .fkIdAnalyse(1L)
                .build();

        TestAnalyse testAnalyse = TestAnalyseMapper.INSTANCE.toEntity(testAnalyseDTO);
        testAnalyse.setAnalyse(analyse);

        when(analyseRepository.findById(1L)).thenReturn(Optional.of(analyse));
        when(testAnalyseRepository.save(any(TestAnalyse.class))).thenReturn(testAnalyse);

        // Exécution
        TestAnalyseDTO result = testAnalyseService.createTestAnalyse(testAnalyseDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Test Analyse 1", result.getNomTest());
        verify(analyseRepository, times(1)).findById(1L);
        verify(testAnalyseRepository, times(1)).save(any(TestAnalyse.class));
    }

    @Test
    void testGetTestAnalyseById() {
        // Données de test
        TestAnalyse testAnalyse = TestAnalyse.builder()
                .id(1L)
                .nomTest("Test Analyse 1")
                .build();

        when(testAnalyseRepository.findById(1L)).thenReturn(Optional.of(testAnalyse));

        // Exécution
        TestAnalyseDTO result = testAnalyseService.getTestAnalyseById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("Test Analyse 1", result.getNomTest());
        verify(testAnalyseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllTestAnalyses() {
        // Données de test
        TestAnalyse testAnalyse1 = TestAnalyse.builder().id(1L).nomTest("Test 1").build();
        TestAnalyse testAnalyse2 = TestAnalyse.builder().id(2L).nomTest("Test 2").build();

        when(testAnalyseRepository.findAll()).thenReturn(List.of(testAnalyse1, testAnalyse2));

        // Exécution
        List<TestAnalyseDTO> result = testAnalyseService.getAllTestAnalyses();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(testAnalyseRepository, times(1)).findAll();
    }

    @Test
    void testGetTestAnalysesByAnalyseId() {
        // Données de test
        TestAnalyse testAnalyse1 = TestAnalyse.builder().id(1L).nomTest("Test 1").build();
        TestAnalyse testAnalyse2 = TestAnalyse.builder().id(2L).nomTest("Test 2").build();

        when(testAnalyseRepository.findByAnalyse_Id(1L)).thenReturn(List.of(testAnalyse1, testAnalyse2));

        // Exécution
        List<TestAnalyseDTO> result = testAnalyseService.getTestAnalysesByAnalyseId(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(testAnalyseRepository, times(1)).findByAnalyse_Id(1L);
    }

    @Test
    void testUpdateTestAnalyse() {
        // Données de test
        Analyse analyse = Analyse.builder().id(1L).nom("Analyse Test").build();
        TestAnalyse existingTestAnalyse = TestAnalyse.builder()
                .id(1L)
                .nomTest("Old Test")
                .analyse(analyse)
                .build();

        TestAnalyseDTO updatedDTO = TestAnalyseDTO.builder()
                .nomTest("Updated Test")
                .sousEpreuve("Sous-épreuve updated")
                .intervalMinDeReference(5.0)
                .intervalMaxDeReference(15.0)
                .uniteDeReference("g/L")
                .details("Updated details")
                .fkIdAnalyse(1L)
                .build();

        when(testAnalyseRepository.findById(1L)).thenReturn(Optional.of(existingTestAnalyse));
        when(analyseRepository.findById(1L)).thenReturn(Optional.of(analyse));
        when(testAnalyseRepository.save(any(TestAnalyse.class))).thenReturn(existingTestAnalyse);

        // Exécution
        TestAnalyseDTO result = testAnalyseService.updateTestAnalyse(1L, updatedDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Updated Test", result.getNomTest());
        verify(testAnalyseRepository, times(1)).findById(1L);
        verify(analyseRepository, times(1)).findById(1L);
        verify(testAnalyseRepository, times(1)).save(any(TestAnalyse.class));
    }

    @Test
    void testDeleteTestAnalyse() {
        // Données de test
        when(testAnalyseRepository.existsById(1L)).thenReturn(true);

        // Exécution
        testAnalyseService.deleteTestAnalyse(1L);

        // Vérifications
        verify(testAnalyseRepository, times(1)).existsById(1L);
        verify(testAnalyseRepository, times(1)).deleteById(1L);
    }
}
