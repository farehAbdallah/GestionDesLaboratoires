package net.chabab.gestionanalyse.mapper;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.entities.Epreuve;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EpreuveMapper {
    EpreuveMapper INSTANCE = Mappers.getMapper(EpreuveMapper.class);

    @Mapping(source = "analyse.id", target = "fkIdAnalyse")
    EpreuveDTO toDto(Epreuve epreuve);

    @Mapping(source = "fkIdAnalyse", target = "analyse.id")
    Epreuve toEntity(EpreuveDTO epreuveDTO);
}
