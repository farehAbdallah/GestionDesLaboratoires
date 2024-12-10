package net.chabab.laboratoireservice.mapper;

import net.chabab.laboratoireservice.dtos.AdresseDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdresseMapper {

    AdresseMapper INSTANCE = Mappers.getMapper(AdresseMapper.class);

    AdresseDTO toDto(Adresse adresse);

    Adresse toEntity(AdresseDTO adresseDTO);
}
