package net.chabab.laboratoireservice.mapper;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.Laboratoire;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LaboratoireMapper {

    LaboratoireMapper INSTANCE = Mappers.getMapper(LaboratoireMapper.class);

    LaboratoireDTO toDto(Laboratoire laboratoire);

    Laboratoire toEntity(LaboratoireDTO laboratoireDTO);
}
