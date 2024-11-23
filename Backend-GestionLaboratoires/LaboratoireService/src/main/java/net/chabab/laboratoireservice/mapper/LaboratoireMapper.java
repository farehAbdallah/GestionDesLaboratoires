package net.chabab.laboratoireservice.mapper;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.Laboratoire;
import java.util.List;
import java.util.stream.Collectors;

public class LaboratoireMapper {

    public static LaboratoireDTO toLaboratoireDTO(Laboratoire laboratoire) {
        return LaboratoireDTO.builder()
                .id(laboratoire.getId())
                .nom(laboratoire.getNom())
                .logo(laboratoire.getLogo())
                .nrc(laboratoire.getNrc())
                .active(laboratoire.isActive())
                .dateActivation(laboratoire.getDateActivation())
                .contacts(laboratoire.getContacts().stream()
                        .map(ContactLaboratoireMapper::toContactLaboratoireDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Laboratoire toLaboratoireEntity(LaboratoireDTO laboratoireDTO) {
        Laboratoire laboratoire = Laboratoire.builder()
                .id(laboratoireDTO.getId())
                .nom(laboratoireDTO.getNom())
                .logo(laboratoireDTO.getLogo())
                .nrc(laboratoireDTO.getNrc())
                .active(laboratoireDTO.isActive())
                .dateActivation(laboratoireDTO.getDateActivation())
                .build();
        return laboratoire;
    }
}
