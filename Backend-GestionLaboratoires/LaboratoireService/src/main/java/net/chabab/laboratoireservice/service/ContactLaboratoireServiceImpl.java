package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;
import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.mapper.ContactLaboratoireMapper;
import net.chabab.laboratoireservice.repository.AdresseRepository;
import net.chabab.laboratoireservice.repository.ContactLaboratoireRepository;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactLaboratoireServiceImpl implements ContactLaboratoireService {

    private final ContactLaboratoireRepository contactRepository;
    private final LaboratoireRepository laboratoireRepository;
    private final AdresseRepository adresseRepository;

    // Utilisation d'un constructeur unique pour injecter les dépendances
    public ContactLaboratoireServiceImpl(ContactLaboratoireRepository contactRepository,
                                         LaboratoireRepository laboratoireRepository,
                                         AdresseRepository adresseRepository) {
        this.contactRepository = contactRepository;
        this.laboratoireRepository = laboratoireRepository;
        this.adresseRepository = adresseRepository;
    }

    @Override
    public ContactLaboratoireDTO createContact(ContactLaboratoireDTO contactDTO) {
        if (contactDTO == null) {
            throw new IllegalArgumentException("ContactLaboratoireDTO cannot be null");
        }

        // Vérification des entités associées
        Laboratoire laboratoire = laboratoireRepository.findById(contactDTO.getLaboratoireId())
                .orElseThrow(() -> new RuntimeException("Laboratoire not found"));

        Adresse adresse = adresseRepository.findById(contactDTO.getAdresseId())
                .orElseThrow(() -> new RuntimeException("Adresse not found"));

        // Création de l'entité ContactLaboratoire
        ContactLaboratoire contact = ContactLaboratoireMapper.INSTANCE.toEntity(contactDTO);

        // Association des entités
        contact.setLaboratoire(laboratoire);
        contact.setAdresse(adresse);

        ContactLaboratoire savedContact = contactRepository.save(contact);
        return ContactLaboratoireMapper.INSTANCE.toDto(savedContact);
    }

    @Override
    public ContactLaboratoireDTO getContactById(Long id) {
        ContactLaboratoire contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        return ContactLaboratoireMapper.INSTANCE.toDto(contact);
    }

    @Override
    public List<ContactLaboratoireDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(ContactLaboratoireMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactLaboratoireDTO updateContact(Long id, ContactLaboratoireDTO contactDTO) {
        ContactLaboratoire existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));

        existingContact.setNumTel(contactDTO.getNumTel());
        existingContact.setFax(contactDTO.getFax());
        existingContact.setEmail(contactDTO.getEmail());

        ContactLaboratoire updatedContact = contactRepository.save(existingContact);
        return ContactLaboratoireMapper.INSTANCE.toDto(updatedContact);
    }

    @Override
    public boolean deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
        return true;
    }
}
