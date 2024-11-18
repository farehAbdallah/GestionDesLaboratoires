package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.entites.Epreuve;
import net.chabab.gestionanalyse.repository.EpreuveRepository;
import net.chabab.gestionanalyse.mapper.EpreuveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpreuveService {

    @Autowired
    private EpreuveRepository epreuveRepository;

    // Créer une nouvelle Epreuve
    public EpreuveDTO createEpreuve(EpreuveDTO epreuveDTO) {
        Epreuve epreuve = EpreuveMapper.toEpreuveEntity(epreuveDTO);
        Epreuve savedEpreuve = epreuveRepository.save(epreuve);
        return EpreuveMapper.toEpreuveDTO(savedEpreuve);
    }

    // Obtenir une Epreuve par son ID
    public EpreuveDTO getEpreuveById(Long id) {
        Epreuve epreuve = epreuveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Epreuve not found"));
        return EpreuveMapper.toEpreuveDTO(epreuve);
    }

    // Obtenir toutes les Epreuves
    public List<EpreuveDTO> getAllEpreuves() {
        List<Epreuve> epreuves = epreuveRepository.findAll();
        return epreuves.stream()
                .map(EpreuveMapper::toEpreuveDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour une Epreuve
    public EpreuveDTO updateEpreuve(Long id, EpreuveDTO epreuveDTO) {
        Epreuve existingEpreuve = epreuveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Epreuve not found"));

        existingEpreuve.setNomEpreuve(epreuveDTO.getNomEpreuve());  // Mettre à jour le champ modifié
        existingEpreuve.setAnalyse(EpreuveMapper.toEpreuveEntity(epreuveDTO).getAnalyse());  // Mettre à jour l'analyse associée
        Epreuve updatedEpreuve = epreuveRepository.save(existingEpreuve);
        return EpreuveMapper.toEpreuveDTO(updatedEpreuve);
    }

    // Supprimer une Epreuve
    public void deleteEpreuve(Long id) {
        epreuveRepository.deleteById(id);
    }
}
