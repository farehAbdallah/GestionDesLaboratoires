package net.chabab.patientservice.mappers;

import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.entities.Examin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExaminMapper {

    ExaminMapper INSTANCE = Mappers.getMapper(ExaminMapper.class);

    ExaminDTO toDto(Examin examin);

    Examin toEntity(ExaminDTO examinDTO);
}
