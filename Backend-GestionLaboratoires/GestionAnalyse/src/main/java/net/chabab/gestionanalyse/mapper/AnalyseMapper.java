package net.chabab.gestionanalyse.mapper;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnalyseMapper {
    AnalyseMapper INSTANCE = Mappers.getMapper(AnalyseMapper.class);

    AnalyseDTO toDto(Analyse analyse);
    Analyse toEntity(AnalyseDTO analyseDTO);
}
