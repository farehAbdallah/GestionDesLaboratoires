package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;

import java.util.List;

public interface EpreuveService {
    EpreuveDTO createEpreuve(EpreuveDTO epreuveDTO);
    EpreuveDTO getEpreuveById(Long id);
    List<EpreuveDTO> getAllEpreuves();
    List<EpreuveDTO> getEpreuvesByAnalyseId(Long analyseId);
    EpreuveDTO updateEpreuve(Long id, EpreuveDTO epreuveDTO);
    void deleteEpreuve(Long id);
}
