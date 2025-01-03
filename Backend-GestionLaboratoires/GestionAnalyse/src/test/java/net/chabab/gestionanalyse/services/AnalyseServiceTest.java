package net.chabab.gestionanalyse.services;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.dtos.LaboratoireDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.mapper.AnalyseMapper;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.feign.LaboratoireClient;
import net.chabab.gestionanalyse.service.AnalyseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyseServiceTest {

    @Mock
    private AnalyseRepository analyseRepository;

    @Mock
    private LaboratoireClient laboratoireClient;

    @InjectMocks
    private AnalyseServiceImpl analyseService;

    @Test
    void testCreateAnalyse_Success() {
        // Données de test
        LaboratoireDTO laboratoire = LaboratoireDTO.builder()
                .id(1L)
                .nom("Laboratoire Test")
                .build();

        AnalyseDTO analyseDTO = AnalyseDTO.builder()
                .nom("Analyse 1")
                .description("Description de l'analyse 1")
                .fkIdLaboratoire(1L)
                .build();

        Analyse analyse = AnalyseMapper.INSTANCE.toEntity(analyseDTO);

        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(laboratoire);
        when(analyseRepository.save(any(Analyse.class))).thenReturn(analyse);

        // Exécution
        AnalyseDTO result = analyseService.createAnalyse(analyseDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Analyse 1", result.getNom());
        verify(laboratoireClient, times(1)).getLaboratoireById(1L);
        verify(analyseRepository, times(1)).save(any(Analyse.class));
    }

    @Test
    void testCreateAnalyse_LaboratoireNotFound() {
        AnalyseDTO analyseDTO = AnalyseDTO.builder()
                .nom("Analyse 1")
                .description("Description de l'analyse 1")
                .fkIdLaboratoire(1L)
                .build();

        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(null);

        // Vérification d'exception
        Exception exception = assertThrows(RuntimeException.class, () -> analyseService.createAnalyse(analyseDTO));
        assertEquals("Laboratory with ID 1 does not exist", exception.getMessage());

        verify(laboratoireClient, times(1)).getLaboratoireById(1L);
        verifyNoInteractions(analyseRepository);
    }

    @Test
    void testGetAnalyseById_Success() {
        Analyse analyse = Analyse.builder()
                .id(1L)
                .nom("Analyse 1")
                .description("Description de l'analyse 1")
                .build();

        when(analyseRepository.findById(1L)).thenReturn(Optional.of(analyse));

        // Exécution
        AnalyseDTO result = analyseService.getAnalyseById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("Analyse 1", result.getNom());
        verify(analyseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAnalyseById_NotFound() {
        when(analyseRepository.findById(1L)).thenReturn(Optional.empty());

        // Vérification d'exception
        Exception exception = assertThrows(RuntimeException.class, () -> analyseService.getAnalyseById(1L));
        assertEquals("Analyse not found with id: 1", exception.getMessage());

        verify(analyseRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAnalyse_Success() {
        // Données de test
        Analyse existingAnalyse = Analyse.builder()
                .id(1L)
                .nom("Analyse existante")
                .description("Description existante")
                .fkIdLaboratoire(1L)
                .build();

        AnalyseDTO updatedAnalyseDTO = AnalyseDTO.builder()
                .nom("Analyse mise à jour")
                .description("Nouvelle description")
                .fkIdLaboratoire(1L)
                .build();

        LaboratoireDTO laboratoireDTO = LaboratoireDTO.builder()
                .id(1L)
                .nom("Laboratoire Test")
                .build();

        when(analyseRepository.findById(1L)).thenReturn(Optional.of(existingAnalyse));
        when(laboratoireClient.getLaboratoireById(1L)).thenReturn(laboratoireDTO); // Simule un laboratoire valide
        when(analyseRepository.save(any(Analyse.class))).thenReturn(existingAnalyse);

        // Exécution
        AnalyseDTO result = analyseService.updateAnalyse(1L, updatedAnalyseDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Analyse mise à jour", result.getNom());
        assertEquals("Nouvelle description", result.getDescription());
        verify(analyseRepository, times(1)).findById(1L);
        verify(laboratoireClient, times(1)).getLaboratoireById(1L);
        verify(analyseRepository, times(1)).save(any(Analyse.class));
    }


    @Test
    void testUpdateAnalyse_NotFound() {
        AnalyseDTO updatedDTO = AnalyseDTO.builder()
                .nom("Analyse Updated")
                .description("Description Updated")
                .fkIdLaboratoire(1L)
                .build();

        when(analyseRepository.findById(1L)).thenReturn(Optional.empty());

        // Vérification d'exception
        Exception exception = assertThrows(RuntimeException.class, () -> analyseService.updateAnalyse(1L, updatedDTO));
        assertEquals("Analyse not found with id: 1", exception.getMessage());

        verify(analyseRepository, times(1)).findById(1L);
        verify(analyseRepository, times(0)).save(any(Analyse.class));
    }

    @Test
    void testDeleteAnalyse_Success() {
        when(analyseRepository.existsById(1L)).thenReturn(true);

        // Exécution
        analyseService.deleteAnalyse(1L);

        // Vérifications
        verify(analyseRepository, times(1)).existsById(1L);
        verify(analyseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAnalyse_NotFound() {
        when(analyseRepository.existsById(1L)).thenReturn(false);

        // Vérification d'exception
        Exception exception = assertThrows(RuntimeException.class, () -> analyseService.deleteAnalyse(1L));
        assertEquals("Analyse not found with id: 1", exception.getMessage());

        verify(analyseRepository, times(1)).existsById(1L);
        verify(analyseRepository, times(0)).deleteById(1L);
    }
}