package net.chabab.patientservice.mappers;

import net.chabab.patientservice.dtos.DossierDTO;
import net.chabab.patientservice.entities.Dossier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DossierMapper {
    DossierMapper INSTANCE = Mappers.getMapper(DossierMapper.class);

    @Mapping(source = "patient.id", target = "patientId")
    DossierDTO toDto(Dossier dossier);

    @Mapping(source = "patientId", target = "patient.id")
    Dossier toEntity(DossierDTO dossierDTO);
}
