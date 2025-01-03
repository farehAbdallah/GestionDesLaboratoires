package net.chabab.patientservice.service;

import net.chabab.patientservice.dtos.PatientDTO;
import net.chabab.patientservice.entities.Patient;
import net.chabab.patientservice.mappers.PatientMapper;
import net.chabab.patientservice.repositories.PatientRepository;
import net.chabab.patientservice.services.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void testCreatePatient() {
        // Données de test
        PatientDTO patientDTO = PatientDTO.builder()
                .nomComplet("John Doe")
                .dateNaissance(new Date())
                .lieuDeNaissance("Cityville")
                .sexe("M")
                .typePieceIdentite("CIN")
                .numPieceIdentite("12345678")
                .adresse("123 Main St")
                .numTel("0123456789")
                .email("johndoe@example.com")
                .visiblePour("DOCTOR")
                .build();

        Patient patient = PatientMapper.INSTANCE.toEntity(patientDTO);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Exécution
        PatientDTO result = patientService.createPatient(patientDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("John Doe", result.getNomComplet());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testGetPatientById() {
        // Données de test
        Patient patient = Patient.builder()
                .id(1L)
                .nomComplet("John Doe")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Exécution
        PatientDTO result = patientService.getPatientById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("John Doe", result.getNomComplet());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllPatients() {
        // Données de test
        Patient patient1 = Patient.builder().id(1L).nomComplet("John Doe").build();
        Patient patient2 = Patient.builder().id(2L).nomComplet("Jane Doe").build();

        when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2));

        // Exécution
        List<PatientDTO> result = patientService.getAllPatients();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePatient() {
        // Données de test
        Patient existingPatient = Patient.builder()
                .id(1L)
                .nomComplet("John Doe")
                .build();

        PatientDTO updatedDTO = PatientDTO.builder()
                .nomComplet("John Updated")
                .dateNaissance(new Date())
                .lieuDeNaissance("Updated City")
                .sexe("M")
                .typePieceIdentite("CIN")
                .numPieceIdentite("12345678")
                .adresse("123 Updated St")
                .numTel("9876543210")
                .email("updated@example.com")
                .visiblePour("DOCTOR")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        // Exécution
        PatientDTO result = patientService.updatePatient(1L, updatedDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("John Updated", result.getNomComplet());
        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testDeletePatient() {
        // Données de test
        when(patientRepository.existsById(1L)).thenReturn(true);

        // Exécution
        assertDoesNotThrow(() -> patientService.deletePatient(1L));

        // Vérifications
        verify(patientRepository, times(1)).existsById(1L);
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreatePatient_InvalidEmail() {
        // Données de test
        PatientDTO patientDTO = PatientDTO.builder()
                .email(null) // Email invalide
                .nomComplet("John Doe")
                .build();

        // Exécution et vérification
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> patientService.createPatient(patientDTO));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

}
