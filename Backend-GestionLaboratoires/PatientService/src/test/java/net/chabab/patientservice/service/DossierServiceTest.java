package net.chabab.patientservice.service;

import net.chabab.patientservice.dtos.DossierDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Patient;
import net.chabab.patientservice.mappers.DossierMapper;
import net.chabab.patientservice.repositories.DossierRepository;
import net.chabab.patientservice.repositories.PatientRepository;
import net.chabab.patientservice.services.DossierServiceImpl;
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
class DossierServiceTest {

    @Mock
    private DossierRepository dossierRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private DossierServiceImpl dossierService;

    @Test
    void testCreateDossier() {
        // Données de test
        Patient patient = Patient.builder().id(1L).build();
        Dossier dossier = Dossier.builder()
                .numDossier(1L)
                .fkEmailUtilisateur("test@example.com")
                .date(new Date())
                .patient(patient)
                .build();

        DossierDTO dossierDTO = DossierDTO.builder()
                .fkEmailUtilisateur("test@example.com")
                .patientId(1L)
                .date(new Date())
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(dossierRepository.save(any(Dossier.class))).thenReturn(dossier);

        // Exécution
        DossierDTO result = dossierService.createDossier(dossierDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("test@example.com", result.getFkEmailUtilisateur());
        verify(patientRepository, times(1)).findById(1L);
        verify(dossierRepository, times(1)).save(any(Dossier.class));
    }

    @Test
    void testGetDossierById() {
        // Données de test
        Patient patient = Patient.builder().id(1L).build();
        Dossier dossier = Dossier.builder()
                .numDossier(1L)
                .fkEmailUtilisateur("test@example.com")
                .date(new Date())
                .patient(patient)
                .build();

        when(dossierRepository.findById(1L)).thenReturn(Optional.of(dossier));

        // Exécution
        DossierDTO result = dossierService.getDossierById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("test@example.com", result.getFkEmailUtilisateur());
        verify(dossierRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateDossier() {
        // Données de test
        Patient patient = Patient.builder().id(1L).build();
        Dossier existingDossier = Dossier.builder()
                .numDossier(1L)
                .fkEmailUtilisateur("old@example.com")
                .date(new Date())
                .patient(patient)
                .build();

        DossierDTO updatedDossierDTO = DossierDTO.builder()
                .fkEmailUtilisateur("new@example.com")
                .patientId(1L)
                .date(new Date())
                .build();

        when(dossierRepository.findById(1L)).thenReturn(Optional.of(existingDossier));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(dossierRepository.save(any(Dossier.class))).thenReturn(existingDossier);

        // Exécution
        DossierDTO result = dossierService.updateDossier(1L, updatedDossierDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("new@example.com", result.getFkEmailUtilisateur());
        verify(dossierRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).findById(1L);
        verify(dossierRepository, times(1)).save(any(Dossier.class));
    }

    @Test
    void testDeleteDossier() {
        // Données de test
        when(dossierRepository.existsById(1L)).thenReturn(true);

        // Exécution
        assertDoesNotThrow(() -> dossierService.deleteDossier(1L));

        // Vérifications
        verify(dossierRepository, times(1)).existsById(1L);
        verify(dossierRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllDossiers() {
        // Données de test
        Patient patient = Patient.builder().id(1L).build();
        Dossier dossier1 = Dossier.builder()
                .numDossier(1L)
                .fkEmailUtilisateur("user1@example.com")
                .date(new Date())
                .patient(patient)
                .build();
        Dossier dossier2 = Dossier.builder()
                .numDossier(2L)
                .fkEmailUtilisateur("user2@example.com")
                .date(new Date())
                .patient(patient)
                .build();

        when(dossierRepository.findAll()).thenReturn(List.of(dossier1, dossier2));

        // Exécution
        List<DossierDTO> dossiers = dossierService.getAllDossiers();

        // Vérifications
        assertNotNull(dossiers);
        assertEquals(2, dossiers.size());
        verify(dossierRepository, times(1)).findAll();
    }
}
