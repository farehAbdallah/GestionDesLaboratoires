package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;

import java.util.List;

public interface ContactLaboratoireService {
    ContactLaboratoireDTO createContact(ContactLaboratoireDTO contactDTO);
    ContactLaboratoireDTO getContactById(Long id);
    List<ContactLaboratoireDTO> getAllContacts();
    ContactLaboratoireDTO updateContact(Long id, ContactLaboratoireDTO contactDTO);
    boolean deleteContact(Long id);
}
