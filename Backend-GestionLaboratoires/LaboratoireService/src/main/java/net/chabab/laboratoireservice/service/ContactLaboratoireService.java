package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;
import net.chabab.laboratoireservice.mapper.ContactLaboratoireMapper;
import net.chabab.laboratoireservice.repository.ContactLaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactLaboratoireService {

    @Autowired
    private ContactLaboratoireRepository contactLaboratoireRepository;

    // Créer un nouveau contact pour un laboratoire
    public ContactLaboratoireDTO createContactLaboratoire(ContactLaboratoireDTO contactLaboratoireDTO) {
        ContactLaboratoire contactLaboratoire = ContactLaboratoireMapper.toContactLaboratoireEntity(contactLaboratoireDTO);
        ContactLaboratoire savedContact = contactLaboratoireRepository.save(contactLaboratoire);
        return ContactLaboratoireMapper.toContactLaboratoireDTO(savedContact);
    }

    // Obtenir un contact par son ID
    public ContactLaboratoireDTO getContactLaboratoireById(Long id) {
        ContactLaboratoire contactLaboratoire = contactLaboratoireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactLaboratoire not found"));
        return ContactLaboratoireMapper.toContactLaboratoireDTO(contactLaboratoire);
    }

    // Obtenir tous les contacts
    public List<ContactLaboratoireDTO> getAllContactsLaboratoire() {
        List<ContactLaboratoire> contacts = contactLaboratoireRepository.findAll();
        return contacts.stream()
                .map(ContactLaboratoireMapper::toContactLaboratoireDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour un contact
    public ContactLaboratoireDTO updateContactLaboratoire(Long id, ContactLaboratoireDTO contactLaboratoireDTO) {
        ContactLaboratoire existingContact = contactLaboratoireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactLaboratoire not found"));

        existingContact.setNumTel(contactLaboratoireDTO.getNumTel());
        existingContact.setFax(contactLaboratoireDTO.getFax());
        existingContact.setEmail(contactLaboratoireDTO.getEmail());

        ContactLaboratoire updatedContact = contactLaboratoireRepository.save(existingContact);
        return ContactLaboratoireMapper.toContactLaboratoireDTO(updatedContact);
    }

    // Supprimer un contact
    public void deleteContactLaboratoire(Long id) {
        contactLaboratoireRepository.deleteById(id);
    }
}
