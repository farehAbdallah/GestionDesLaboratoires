package net.chabab.patientservice.service;

import net.chabab.patientservice.dtos.EpreuveDTO;
import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Examin;
import net.chabab.patientservice.feign.EpreuveFeignClient;
import net.chabab.patientservice.mappers.ExaminMapper;
import net.chabab.patientservice.repositories.DossierRepository;
import net.chabab.patientservice.repositories.ExaminRepository;
import net.chabab.patientservice.services.ExaminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminServiceTest {

    @Mock
    private ExaminRepository examinRepository;

    @Mock
    private DossierRepository dossierRepository;

    @Mock
    private EpreuveFeignClient epreuveFeignClient;

    @InjectMocks
    private ExaminServiceImpl examinService;

    @Test
    void testCreateExamin() {
        // Données de test
        Dossier dossier = Dossier.builder().numDossier(1L).fkEmailUtilisateur("test@example.com").date(new Date()).build();
        EpreuveDTO epreuveDTO = EpreuveDTO.builder().id(1L).nom("Epreuve Test").build();

        ExaminDTO examinDTO = ExaminDTO.builder()
                .fkNumDossier(1L)
                .fkIdEpreuve(1L)
                .fkIdTestAnalyse(2L)
                .resultat("Résultat Test")
                .build();

        Examin examin = ExaminMapper.INSTANCE.toEntity(examinDTO);
        examin.setDossier(dossier);

        when(dossierRepository.findById(1L)).thenReturn(Optional.of(dossier));
        when(epreuveFeignClient.getEpreuveById(1L)).thenReturn(epreuveDTO);
        when(examinRepository.save(any(Examin.class))).thenReturn(examin);

        // Exécution
        ExaminDTO result = examinService.createExamin(examinDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Résultat Test", result.getResultat());
        verify(dossierRepository, times(1)).findById(1L);
        verify(epreuveFeignClient, times(1)).getEpreuveById(1L);
        verify(examinRepository, times(1)).save(any(Examin.class));
    }

    @Test
    void testGetExaminById() {
        // Données de test
        Examin examin = Examin.builder()
                .id(1L)
                .fkIdEpreuve(1L)
                .fkIdTestAnalyse(2L)
                .resultat("Résultat Test")
                .build();

        when(examinRepository.findById(1L)).thenReturn(Optional.of(examin));

        // Exécution
        ExaminDTO result = examinService.getExaminById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("Résultat Test", result.getResultat());
        verify(examinRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllExamins() {
        // Données de test
        Examin examin1 = Examin.builder().id(1L).resultat("Résultat 1").build();
        Examin examin2 = Examin.builder().id(2L).resultat("Résultat 2").build();

        when(examinRepository.findAll()).thenReturn(List.of(examin1, examin2));

        // Exécution
        List<ExaminDTO> result = examinService.getAllExamins();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(examinRepository, times(1)).findAll();
    }

    @Test
    void testDeleteExamin() {
        // Données de test
        when(examinRepository.existsById(1L)).thenReturn(true);

        // Exécution
        assertDoesNotThrow(() -> examinService.deleteExamin(1L));

        // Vérifications
        verify(examinRepository, times(1)).existsById(1L);
        verify(examinRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateExamin_DossierNotFound() {
        // Données de test
        ExaminDTO examinDTO = ExaminDTO.builder()
                .fkNumDossier(1L)
                .fkIdEpreuve(1L)
                .fkIdTestAnalyse(2L)
                .resultat("Résultat Test")
                .build();

        when(dossierRepository.findById(1L)).thenReturn(Optional.empty());

        // Exécution et vérification de l'exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> examinService.createExamin(examinDTO));
        assertEquals("Dossier not found", exception.getMessage());
        verify(dossierRepository, times(1)).findById(1L);
    }
}
