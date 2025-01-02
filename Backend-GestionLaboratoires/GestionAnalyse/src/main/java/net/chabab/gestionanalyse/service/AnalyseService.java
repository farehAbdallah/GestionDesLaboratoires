package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.entities.Epreuve;
import net.chabab.gestionanalyse.entities.TestAnalyse;

import java.util.List;

public interface AnalyseService {
    AnalyseDTO createAnalyse(AnalyseDTO analyseDTO);
    AnalyseDTO getAnalyseById(Long id);
    List<AnalyseDTO> getAllAnalyses();
    AnalyseDTO updateAnalyse(Long id, AnalyseDTO analyseDTO);
    void deleteAnalyse(Long id);
    void sendAnalyseDetails(Analyse analyse, TestAnalyse testAnalyse, Epreuve epreuve);
}
