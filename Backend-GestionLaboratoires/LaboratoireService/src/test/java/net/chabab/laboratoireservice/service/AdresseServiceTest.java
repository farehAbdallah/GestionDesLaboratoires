package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.AdresseDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import net.chabab.laboratoireservice.mapper.AdresseMapper;
import net.chabab.laboratoireservice.repository.AdresseRepository;
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
class AdresseServiceTest {

    @Mock
    private AdresseRepository adresseRepository;

    @InjectMocks
    private AdresseServiceImpl adresseService;

    @Test
    void testCreateAdresse() {
        // Données de test
        AdresseDTO adresseDTO = AdresseDTO.builder()
                .numVoie("10")
                .nomVoie("Rue des Lilas")
                .codePostal("12345")
                .ville("Paris")
                .commune("Commune A")
                .build();

        Adresse adresse = AdresseMapper.INSTANCE.toEntity(adresseDTO);
        when(adresseRepository.save(any(Adresse.class))).thenReturn(adresse);

        // Exécution
        AdresseDTO result = adresseService.createAdresse(adresseDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("10", result.getNumVoie());
        verify(adresseRepository, times(1)).save(any(Adresse.class));
    }

    @Test
    void testGetAdresseById() {
        // Données de test
        Adresse adresse = Adresse.builder()
                .id(1L)
                .numVoie("10")
                .nomVoie("Rue des Lilas")
                .codePostal("12345")
                .ville("Paris")
                .commune("Commune A")
                .build();

        when(adresseRepository.findById(1L)).thenReturn(Optional.of(adresse));

        // Exécution
        AdresseDTO result = adresseService.getAdresseById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("10", result.getNumVoie());
        verify(adresseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllAdresses() {
        // Données de test
        List<Adresse> adresses = Arrays.asList(
                Adresse.builder().id(1L).numVoie("10").nomVoie("Rue des Lilas").build(),
                Adresse.builder().id(2L).numVoie("20").nomVoie("Avenue des Fleurs").build()
        );

        when(adresseRepository.findAll()).thenReturn(adresses);

        // Exécution
        List<AdresseDTO> result = adresseService.getAllAdresses();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(adresseRepository, times(1)).findAll();
    }

    @Test
    void testUpdateAdresse() {
        // Données de test
        Adresse existingAdresse = Adresse.builder()
                .id(1L)
                .numVoie("10")
                .nomVoie("Rue des Lilas")
                .codePostal("12345")
                .ville("Paris")
                .commune("Commune A")
                .build();

        AdresseDTO updatedDTO = AdresseDTO.builder()
                .numVoie("15")
                .nomVoie("Boulevard des Champs")
                .codePostal("54321")
                .ville("Lyon")
                .commune("Commune B")
                .build();

        when(adresseRepository.findById(1L)).thenReturn(Optional.of(existingAdresse));
        when(adresseRepository.save(any(Adresse.class))).thenReturn(existingAdresse);

        // Exécution
        AdresseDTO result = adresseService.updateAdresse(1L, updatedDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("15", result.getNumVoie());
        verify(adresseRepository, times(1)).findById(1L);
        verify(adresseRepository, times(1)).save(any(Adresse.class));
    }

    @Test
    void testDeleteAdresse() {
        // Données de test
        when(adresseRepository.existsById(1L)).thenReturn(true);

        // Exécution
        boolean result = adresseService.deleteAdresse(1L);

        // Vérifications
        assertTrue(result);
        verify(adresseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAdresseNotFound() {
        // Données de test
        when(adresseRepository.existsById(1L)).thenReturn(false);

        // Exécution et vérification
        Exception exception = assertThrows(RuntimeException.class, () -> adresseService.deleteAdresse(1L));
        assertEquals("Adresse not found with id: 1", exception.getMessage());
        verify(adresseRepository, times(1)).existsById(1L);
        verify(adresseRepository, never()).deleteById(anyLong());
    }
}