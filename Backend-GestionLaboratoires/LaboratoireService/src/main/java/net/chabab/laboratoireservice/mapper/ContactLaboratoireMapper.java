package net.chabab.laboratoireservice.mapper;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactLaboratoireMapper {

    ContactLaboratoireMapper INSTANCE = Mappers.getMapper(ContactLaboratoireMapper.class);
    @Mapping(target = "laboratoireId", source = "laboratoire.id")
    @Mapping(target = "adresseId", source = "adresse.id")
    ContactLaboratoireDTO toDto(ContactLaboratoire contact);

    @Mapping(target = "laboratoire", ignore = true) // Ignoré pour être traité manuellement
    @Mapping(target = "adresse", ignore = true)    // Ignoré pour être traité manuellement
    ContactLaboratoire toEntity(ContactLaboratoireDTO contactDTO);
}
