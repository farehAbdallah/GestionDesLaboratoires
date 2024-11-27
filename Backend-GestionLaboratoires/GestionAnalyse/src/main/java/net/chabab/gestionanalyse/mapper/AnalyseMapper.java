package net.chabab.gestionanalyse.mapper;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.entites.Analyse;
import net.chabab.gestionanalyse.entites.Epreuve;

import java.util.List;
import java.util.stream.Collectors;

public class AnalyseMapper {

    // Convertir de l'entité Analyse en DTO Analyse
    public static AnalyseDTO toAnalyseDTO(Analyse analyse) {
        List<EpreuveDTO> epreuvesDTO = analyse.getEpreuves().stream()
                .map(AnalyseMapper::toEpreuveDTO)
                .collect(Collectors.toList());

        return AnalyseDTO.builder()
                .id(analyse.getId())
                .nom(analyse.getNom())
                .description(analyse.getDescription())
                .epreuves(epreuvesDTO)
                .build();
    }

    // Convertir de l'entité Epreuve en DTO Epreuve
    public static EpreuveDTO toEpreuveDTO(Epreuve epreuve) {
        return EpreuveDTO.builder()
                .id(epreuve.getId())
                .nomEpreuve(epreuve.getNomEpreuve())
                .build();
    }

    // Convertir de DTO Analyse en Entité Analyse
    public static Analyse toAnalyseEntity(AnalyseDTO analyseDTO) {
        Analyse analyse = Analyse.builder()
                .id(analyseDTO.getId())
                .nom(analyseDTO.getNom())
                .description(analyseDTO.getDescription())
                .build();
        // Optionally handle Epreuves here if needed
        return analyse;
    }
}
