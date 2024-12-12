package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.dtos.LaboratoireDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.mapper.AnalyseMapper;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.feign.LaboratoireClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyseServiceImpl implements AnalyseService {

    @Autowired
    private AnalyseRepository analyseRepository;

    @Autowired
    private LaboratoireClient laboratoireClient;

    @Override
    public AnalyseDTO createAnalyse(AnalyseDTO analyseDTO) {
        if (analyseDTO.getFkIdLaboratoire() == null) {
            throw new IllegalArgumentException("Laboratory ID cannot be null");
        }

        // Vérification de l'existence du laboratoire via Feign Client
        LaboratoireDTO laboratoire = laboratoireClient.getLaboratoireById(analyseDTO.getFkIdLaboratoire());
        if (laboratoire == null) {
            throw new RuntimeException("Laboratory with ID " + analyseDTO.getFkIdLaboratoire() + " does not exist");
        }

        Analyse analyse = AnalyseMapper.INSTANCE.toEntity(analyseDTO);
        Analyse savedAnalyse = analyseRepository.save(analyse);
        return AnalyseMapper.INSTANCE.toDto(savedAnalyse);
    }

    @Override
    public AnalyseDTO getAnalyseById(Long id) {
        Analyse analyse = analyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analyse not found with id: " + id));
        return AnalyseMapper.INSTANCE.toDto(analyse);
    }

    @Override
    public List<AnalyseDTO> getAllAnalyses() {
        return analyseRepository.findAll()
                .stream()
                .map(AnalyseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AnalyseDTO updateAnalyse(Long id, AnalyseDTO analyseDTO) {
        Analyse analyse = analyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analyse not found with id: " + id));

        if (analyseDTO.getFkIdLaboratoire() != null) {
            // Vérification de l'existence du laboratoire
            LaboratoireDTO laboratoire = laboratoireClient.getLaboratoireById(analyseDTO.getFkIdLaboratoire());
            if (laboratoire == null) {
                throw new RuntimeException("Laboratory with ID " + analyseDTO.getFkIdLaboratoire() + " does not exist");
            }
        }

        analyse.setNom(analyseDTO.getNom());
        analyse.setDescription(analyseDTO.getDescription());
        analyse.setFkIdLaboratoire(analyseDTO.getFkIdLaboratoire());

        Analyse updatedAnalyse = analyseRepository.save(analyse);
        return AnalyseMapper.INSTANCE.toDto(updatedAnalyse);
    }

    @Override
    public void deleteAnalyse(Long id) {
        if (!analyseRepository.existsById(id)) {
            throw new RuntimeException("Analyse not found with id: " + id);
        }
        analyseRepository.deleteById(id);
    }
}
