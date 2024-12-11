package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.entities.Epreuve;
import net.chabab.gestionanalyse.mapper.EpreuveMapper;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.repository.EpreuveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpreuveServiceImpl implements EpreuveService {

    @Autowired
    private EpreuveRepository epreuveRepository;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Override
    public EpreuveDTO createEpreuve(EpreuveDTO epreuveDTO) {
        Analyse analyse = analyseRepository.findById(epreuveDTO.getFkIdAnalyse())
                .orElseThrow(() -> new RuntimeException("Analyse not found with id: " + epreuveDTO.getFkIdAnalyse()));

        Epreuve epreuve = EpreuveMapper.INSTANCE.toEntity(epreuveDTO);
        epreuve.setAnalyse(analyse);

        Epreuve savedEpreuve = epreuveRepository.save(epreuve);
        return EpreuveMapper.INSTANCE.toDto(savedEpreuve);
    }

    @Override
    public EpreuveDTO getEpreuveById(Long id) {
        Epreuve epreuve = epreuveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Epreuve not found with id: " + id));
        return EpreuveMapper.INSTANCE.toDto(epreuve);
    }

    @Override
    public List<EpreuveDTO> getAllEpreuves() {
        return epreuveRepository.findAll()
                .stream()
                .map(EpreuveMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EpreuveDTO> getEpreuvesByAnalyseId(Long analyseId) {
        return epreuveRepository.findByAnalyseId(analyseId)
                .stream()
                .map(EpreuveMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EpreuveDTO updateEpreuve(Long id, EpreuveDTO epreuveDTO) {
        Epreuve epreuve = epreuveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Epreuve not found with id: " + id));

        Analyse analyse = analyseRepository.findById(epreuveDTO.getFkIdAnalyse())
                .orElseThrow(() -> new RuntimeException("Analyse not found with id: " + epreuveDTO.getFkIdAnalyse()));

        epreuve.setNom(epreuveDTO.getNom());
        epreuve.setAnalyse(analyse);

        Epreuve updatedEpreuve = epreuveRepository.save(epreuve);
        return EpreuveMapper.INSTANCE.toDto(updatedEpreuve);
    }

    @Override
    public void deleteEpreuve(Long id) {
        if (!epreuveRepository.existsById(id)) {
            throw new RuntimeException("Epreuve not found with id: " + id);
        }
        epreuveRepository.deleteById(id);
    }
}
