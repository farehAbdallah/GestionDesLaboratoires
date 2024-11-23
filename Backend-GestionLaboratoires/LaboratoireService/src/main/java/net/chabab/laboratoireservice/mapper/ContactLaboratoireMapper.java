package net.chabab.laboratoireservice.mapper;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;

public class ContactLaboratoireMapper {

    public static ContactLaboratoireDTO toContactLaboratoireDTO(ContactLaboratoire contactLaboratoire) {
        return ContactLaboratoireDTO.builder()
                .id(contactLaboratoire.getId())
                .numTel(contactLaboratoire.getNumTel())
                .fax(contactLaboratoire.getFax())
                .email(contactLaboratoire.getEmail())
                .build();
    }

    public static ContactLaboratoire toContactLaboratoireEntity(ContactLaboratoireDTO contactLaboratoireDTO) {
        ContactLaboratoire contactLaboratoire = ContactLaboratoire.builder()
                .id(contactLaboratoireDTO.getId())
                .numTel(contactLaboratoireDTO.getNumTel())
                .fax(contactLaboratoireDTO.getFax())
                .email(contactLaboratoireDTO.getEmail())
                .build();
        return contactLaboratoire;
    }
}
