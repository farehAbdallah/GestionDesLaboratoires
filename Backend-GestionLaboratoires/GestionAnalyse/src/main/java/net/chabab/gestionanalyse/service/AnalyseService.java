package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.entites.Analyse;
import net.chabab.gestionanalyse.entites.Epreuve;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.mapper.AnalyseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalyseService {

    @Autowired
    private AnalyseRepository analyseRepository;

    // Créer une nouvelle Analyse
    public AnalyseDTO createAnalyse(AnalyseDTO analyseDTO) {
        Analyse analyse = AnalyseMapper.toAnalyseEntity(analyseDTO);

        // Handling epreuves if necessary, you can add logic for saving epreuves here
        if (analyseDTO.getEpreuves() != null) {
            List<Epreuve> epreuves = analyseDTO.getEpreuves().stream()
                    .map(this::toEpreuveEntity)
                    .collect(Collectors.toList());
            analyse.setEpreuves(epreuves);
        }

        Analyse savedAnalyse = analyseRepository.save(analyse);
        return AnalyseMapper.toAnalyseDTO(savedAnalyse);
    }

    // Obtenir une Analyse par son ID
    public AnalyseDTO getAnalyseById(Long id) {
        Analyse analyse = analyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analyse not found"));
        return AnalyseMapper.toAnalyseDTO(analyse);
    }

    // Obtenir toutes les Analyses
    public List<AnalyseDTO> getAllAnalyses() {
        List<Analyse> analyses = analyseRepository.findAll();
        return analyses.stream()
                .map(AnalyseMapper::toAnalyseDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour une Analyse
    public AnalyseDTO updateAnalyse(Long id, AnalyseDTO analyseDTO) {
        Analyse existingAnalyse = analyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analyse not found"));

        existingAnalyse.setNom(analyseDTO.getNom());
        existingAnalyse.setDescription(analyseDTO.getDescription());
        Analyse updatedAnalyse = analyseRepository.save(existingAnalyse);
        return AnalyseMapper.toAnalyseDTO(updatedAnalyse);
    }

    // Supprimer une Analyse
    public void deleteAnalyse(Long id) {
        analyseRepository.deleteById(id);
    }

    // Convertir EpreuveDTO en Epreuve
    private Epreuve toEpreuveEntity(EpreuveDTO epreuveDTO) {
        Epreuve epreuve = new Epreuve();
        epreuve.setNomEpreuve(epreuveDTO.getNomEpreuve());
        return epreuve;
    }
}
