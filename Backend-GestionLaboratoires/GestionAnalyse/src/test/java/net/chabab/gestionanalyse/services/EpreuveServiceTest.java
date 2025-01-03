package net.chabab.gestionanalyse.services;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.entities.Epreuve;
import net.chabab.gestionanalyse.mapper.EpreuveMapper;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.repository.EpreuveRepository;
import net.chabab.gestionanalyse.service.EpreuveServiceImpl;
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
class EpreuveServiceTest {

    @Mock
    private EpreuveRepository epreuveRepository;

    @Mock
    private AnalyseRepository analyseRepository;

    @InjectMocks
    private EpreuveServiceImpl epreuveService;

    @Test
    void testCreateEpreuve() {
        // Données de test
        Analyse analyse = Analyse.builder().id(1L).nom("Analyse Test").build();

        EpreuveDTO epreuveDTO = EpreuveDTO.builder()
                .nom("Epreuve Test")
                .fkIdAnalyse(1L)
                .build();

        Epreuve epreuve = EpreuveMapper.INSTANCE.toEntity(epreuveDTO);
        epreuve.setAnalyse(analyse);

        when(analyseRepository.findById(1L)).thenReturn(Optional.of(analyse));
        when(epreuveRepository.save(any(Epreuve.class))).thenReturn(epreuve);

        // Exécution
        EpreuveDTO result = epreuveService.createEpreuve(epreuveDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Epreuve Test", result.getNom());
        verify(analyseRepository, times(1)).findById(1L);
        verify(epreuveRepository, times(1)).save(any(Epreuve.class));
    }

    @Test
    void testGetEpreuveById() {
        // Données de test
        Epreuve epreuve = Epreuve.builder()
                .id(1L)
                .nom("Epreuve Test")
                .build();

        when(epreuveRepository.findById(1L)).thenReturn(Optional.of(epreuve));

        // Exécution
        EpreuveDTO result = epreuveService.getEpreuveById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("Epreuve Test", result.getNom());
        verify(epreuveRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllEpreuves() {
        // Données de test
        Epreuve epreuve1 = Epreuve.builder().id(1L).nom("Epreuve 1").build();
        Epreuve epreuve2 = Epreuve.builder().id(2L).nom("Epreuve 2").build();

        when(epreuveRepository.findAll()).thenReturn(List.of(epreuve1, epreuve2));

        // Exécution
        List<EpreuveDTO> result = epreuveService.getAllEpreuves();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(epreuveRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEpreuve() {
        // Données de test
        Analyse analyse = Analyse.builder().id(1L).nom("Analyse Test").build();
        Epreuve existingEpreuve = Epreuve.builder().id(1L).nom("Old Epreuve").analyse(analyse).build();

        EpreuveDTO updatedDTO = EpreuveDTO.builder()
                .nom("Updated Epreuve")
                .fkIdAnalyse(1L)
                .build();

        when(epreuveRepository.findById(1L)).thenReturn(Optional.of(existingEpreuve));
        when(analyseRepository.findById(1L)).thenReturn(Optional.of(analyse));
        when(epreuveRepository.save(any(Epreuve.class))).thenReturn(existingEpreuve);

        // Exécution
        EpreuveDTO result = epreuveService.updateEpreuve(1L, updatedDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Updated Epreuve", result.getNom());
        verify(epreuveRepository, times(1)).findById(1L);
        verify(analyseRepository, times(1)).findById(1L);
        verify(epreuveRepository, times(1)).save(any(Epreuve.class));
    }

    @Test
    void testDeleteEpreuve() {
        // Données de test
        when(epreuveRepository.existsById(1L)).thenReturn(true);

        // Exécution
        epreuveService.deleteEpreuve(1L);

        // Vérifications
        verify(epreuveRepository, times(1)).existsById(1L);
        verify(epreuveRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetEpreuvesByAnalyseId() {
        // Données de test
        Epreuve epreuve1 = Epreuve.builder().id(1L).nom("Epreuve 1").build();
        Epreuve epreuve2 = Epreuve.builder().id(2L).nom("Epreuve 2").build();

        when(epreuveRepository.findByAnalyseId(1L)).thenReturn(List.of(epreuve1, epreuve2));

        // Exécution
        List<EpreuveDTO> result = epreuveService.getEpreuvesByAnalyseId(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(epreuveRepository, times(1)).findByAnalyseId(1L);
    }
}
