package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;
import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.repository.AdresseRepository;
import net.chabab.laboratoireservice.repository.ContactLaboratoireRepository;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactLaboratoireServiceTest {

    @Mock
    private ContactLaboratoireRepository contactRepository;

    @Mock
    private LaboratoireRepository laboratoireRepository;

    @Mock
    private AdresseRepository adresseRepository;

    @InjectMocks
    private ContactLaboratoireServiceImpl contactLaboratoireService;

    @Test
    void testCreateContact() {
        // Préparation des données
        Laboratoire laboratoire = Laboratoire.builder().id(1L).nom("Laboratoire Test").build();
        Adresse adresse = Adresse.builder().id(1L).ville("Ville Test").build();

        ContactLaboratoireDTO contactDTO = ContactLaboratoireDTO.builder()
                .email("contact@test.com")
                .numTel("0123456789")
                .fax("0987654321")
                .laboratoireId(1L)
                .adresseId(1L)
                .build();

        ContactLaboratoire contact = new ContactLaboratoire();
        contact.setEmail(contactDTO.getEmail());
        contact.setNumTel(contactDTO.getNumTel());

        when(laboratoireRepository.findById(1L)).thenReturn(Optional.of(laboratoire));
        when(adresseRepository.findById(1L)).thenReturn(Optional.of(adresse));
        when(contactRepository.save(any(ContactLaboratoire.class))).thenReturn(contact);

        // Exécution
        ContactLaboratoireDTO result = contactLaboratoireService.createContact(contactDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("contact@test.com", result.getEmail());
        verify(laboratoireRepository, times(1)).findById(1L);
        verify(adresseRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(any(ContactLaboratoire.class));
    }

    @Test
    void testGetContactById() {
        // Préparation des données
        ContactLaboratoire contact = new ContactLaboratoire();
        contact.setId(1L);
        contact.setEmail("contact@test.com");

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        // Exécution
        ContactLaboratoireDTO result = contactLaboratoireService.getContactById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("contact@test.com", result.getEmail());
        verify(contactRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateContact() {
        // Préparation des données
        ContactLaboratoire existingContact = new ContactLaboratoire();
        existingContact.setId(1L);
        existingContact.setEmail("contact@test.com");

        ContactLaboratoireDTO updatedDTO = ContactLaboratoireDTO.builder()
                .email("updated@test.com")
                .numTel("1234567890")
                .fax("0987654321")
                .build();

        when(contactRepository.findById(1L)).thenReturn(Optional.of(existingContact));
        when(contactRepository.save(any(ContactLaboratoire.class))).thenReturn(existingContact);

        // Exécution
        ContactLaboratoireDTO result = contactLaboratoireService.updateContact(1L, updatedDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("updated@test.com", result.getEmail());
        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(any(ContactLaboratoire.class));
    }

    @Test
    void testDeleteContact() {
        // Préparation des données
        when(contactRepository.existsById(1L)).thenReturn(true);

        // Exécution
        boolean result = contactLaboratoireService.deleteContact(1L);

        // Vérifications
        assertTrue(result);
        verify(contactRepository, times(1)).deleteById(1L);
    }
}