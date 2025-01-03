package net.chabab.patientservice.mappers;

import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.entities.Examin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExaminMapper {

    ExaminMapper INSTANCE = Mappers.getMapper(ExaminMapper.class);

    @Mapping(source = "dossier.numDossier", target = "fkNumDossier")
    ExaminDTO toDto(Examin examin);

    @Mapping(source = "fkNumDossier", target = "dossier.numDossier")
    Examin toEntity(ExaminDTO examinDTO);
}

