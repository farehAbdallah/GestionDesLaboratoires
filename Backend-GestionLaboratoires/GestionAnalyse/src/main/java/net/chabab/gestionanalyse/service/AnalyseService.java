package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;

import java.util.List;

public interface AnalyseService {
    AnalyseDTO createAnalyse(AnalyseDTO analyseDTO);
    AnalyseDTO getAnalyseById(Long id);
    List<AnalyseDTO> getAllAnalyses();
    AnalyseDTO updateAnalyse(Long id, AnalyseDTO analyseDTO);
    void deleteAnalyse(Long id);
}
