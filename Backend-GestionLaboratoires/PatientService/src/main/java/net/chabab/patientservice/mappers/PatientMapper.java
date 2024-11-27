package net.chabab.patientservice.mappers;

import net.chabab.patientservice.dtos.PatientDTO;
import net.chabab.patientservice.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient toEntity(PatientDTO dto);

    PatientDTO toDto(Patient entity);
}
