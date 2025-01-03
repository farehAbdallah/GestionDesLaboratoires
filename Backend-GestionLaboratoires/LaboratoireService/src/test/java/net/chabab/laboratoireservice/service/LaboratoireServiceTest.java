package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.mapper.LaboratoireMapper;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaboratoireServiceTest {

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @InjectMocks
    private LaboratoireServiceImpl laboratoireService;

    @Test
    void testCreateLaboratoire() {
        // Données de test
        LaboratoireDTO laboratoireDTO = LaboratoireDTO.builder()
                .nrc("NRC123")
                .nom("Laboratoire Test")
                .logo("logo.png")
                .active(true)
                .dateActivation("2024-01-01")
                .build();

        Laboratoire laboratoire = LaboratoireMapper.INSTANCE.toEntity(laboratoireDTO);

        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(laboratoire);

        // Exécution
        LaboratoireDTO result = laboratoireService.createLaboratoire(laboratoireDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Laboratoire Test", result.getNom());
        verify(laboratoireRepository, times(1)).save(any(Laboratoire.class));
    }

    @Test
    void testGetLaboratoireById() {
        // Données de test
        Laboratoire laboratoire = Laboratoire.builder()
                .id(1L)
                .nrc("NRC123")
                .nom("Laboratoire Test")
                .build();

        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(laboratoire));

        // Exécution
        LaboratoireDTO result = laboratoireService.getLaboratoireById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("Laboratoire Test", result.getNom());
        verify(laboratoireRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllLaboratoires() {
        // Données de test
        List<Laboratoire> laboratoires = Arrays.asList(
                Laboratoire.builder().id(1L).nrc("NRC123").nom("Lab 1").build(),
                Laboratoire.builder().id(2L).nrc("NRC456").nom("Lab 2").build()
        );

        when(laboratoireRepository.findAll()).thenReturn(laboratoires);

        // Exécution
        List<LaboratoireDTO> result = laboratoireService.getAllLaboratoires();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(laboratoireRepository, times(1)).findAll();
    }

    @Test
    void testUpdateLaboratoire() {
        // Données de test
        Laboratoire existingLaboratoire = Laboratoire.builder()
                .id(1L)
                .nrc("NRC123")
                .nom("Old Name")
                .build();

        LaboratoireDTO updatedDTO = LaboratoireDTO.builder()
                .nrc("NRC456")
                .nom("Updated Name")
                .logo("new_logo.png")
                .active(false)
                .dateActivation("2024-02-01")
                .build();

        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(existingLaboratoire));
        when(laboratoireRepository.save(any(Laboratoire.class))).thenReturn(existingLaboratoire);

        // Exécution
        LaboratoireDTO result = laboratoireService.updateLaboratoire(1L, updatedDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Updated Name", result.getNom());
        verify(laboratoireRepository, times(1)).findById(1L);
        verify(laboratoireRepository, times(1)).save(any(Laboratoire.class));
    }

    @Test
    void testDeleteLaboratoire() {
        // Données de test
        when(laboratoireRepository.existsById(1L)).thenReturn(true);

        // Exécution
        boolean result = laboratoireService.deleteLaboratoire(1L);

        // Vérifications
        assertTrue(result);
        verify(laboratoireRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLaboratoireNotFound() {
        // Données de test
        when(laboratoireRepository.existsById(1L)).thenReturn(false);

        // Exécution et vérification
        Exception exception = assertThrows(RuntimeException.class, () -> laboratoireService.deleteLaboratoire(1L));
        assertEquals("Laboratoire not found with id: 1", exception.getMessage());
        verify(laboratoireRepository, times(1)).existsById(1L);
        verify(laboratoireRepository, never()).deleteById(anyLong());
    }
}