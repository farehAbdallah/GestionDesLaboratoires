package net.chabab.laboratoireservice.mapper;

import net.chabab.laboratoireservice.dtos.AdresseDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import java.util.List;
import java.util.stream.Collectors;

public class AdresseMapper {

    public static AdresseDTO toAdresseDTO(Adresse adresse) {
        return AdresseDTO.builder()
                .id(adresse.getId())
                .numVoie(adresse.getNumVoie())
                .nomVoie(adresse.getNomVoie())
                .codePostal(adresse.getCodePostal())
                .ville(adresse.getVille())
                .commune(adresse.getCommune())
                .contacts(adresse.getContacts().stream()
                        .map(ContactLaboratoireMapper::toContactLaboratoireDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Adresse toAdresseEntity(AdresseDTO adresseDTO) {
        Adresse adresse = Adresse.builder()
                .id(adresseDTO.getId())
                .numVoie(adresseDTO.getNumVoie())
                .nomVoie(adresseDTO.getNomVoie())
                .codePostal(adresseDTO.getCodePostal())
                .ville(adresseDTO.getVille())
                .commune(adresseDTO.getCommune())
                .build();
        return adresse;
    }
}
