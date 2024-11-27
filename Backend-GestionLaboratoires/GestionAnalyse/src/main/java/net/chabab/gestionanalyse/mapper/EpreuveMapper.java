package net.chabab.gestionanalyse.mapper;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.entites.Epreuve;
import net.chabab.gestionanalyse.dtos.AnalyseDTO;

public class EpreuveMapper {

    // Convertir de l'entité Epreuve en DTO Epreuve
    public static EpreuveDTO toEpreuveDTO(Epreuve epreuve) {
        return EpreuveDTO.builder()
                .id(epreuve.getId())
                .nomEpreuve(epreuve.getNomEpreuve())  // Champ modifié ici
                .analyse(AnalyseMapper.toAnalyseDTO(epreuve.getAnalyse()))  // Associe l'analyse correspondante
                .build();
    }

    // Convertir de DTO Epreuve en Entité Epreuve
    public static Epreuve toEpreuveEntity(EpreuveDTO epreuveDTO) {
        return Epreuve.builder()
                .id(epreuveDTO.getId())
                .nomEpreuve(epreuveDTO.getNomEpreuve())  // Champ modifié ici
                .analyse(AnalyseMapper.toAnalyseEntity(epreuveDTO.getAnalyse()))  // Associe l'entité analyse correspondante
                .build();
    }
}
