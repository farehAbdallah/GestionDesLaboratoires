package net.chabab.gestionanalyse.mapper;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;
import net.chabab.gestionanalyse.entities.TestAnalyse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestAnalyseMapper {
    TestAnalyseMapper INSTANCE = Mappers.getMapper(TestAnalyseMapper.class);

    @Mapping(source = "analyse.id", target = "fkIdAnalyse")
    TestAnalyseDTO toDto(TestAnalyse testAnalyse);

    @Mapping(source = "fkIdAnalyse", target = "analyse.id")
    TestAnalyse toEntity(TestAnalyseDTO testAnalyseDTO);
}
