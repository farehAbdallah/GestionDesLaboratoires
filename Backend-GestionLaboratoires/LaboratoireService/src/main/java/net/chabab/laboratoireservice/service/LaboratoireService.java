package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import net.chabab.laboratoireservice.mapper.LaboratoireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LaboratoireService {

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    // Créer un nouveau laboratoire
    public LaboratoireDTO createLaboratoire(LaboratoireDTO laboratoireDTO) {
        Laboratoire laboratoire = LaboratoireMapper.toLaboratoireEntity(laboratoireDTO);
        Laboratoire savedLaboratoire = laboratoireRepository.save(laboratoire);
        return LaboratoireMapper.toLaboratoireDTO(savedLaboratoire);
    }

    // Obtenir un laboratoire par son ID
    public LaboratoireDTO getLaboratoireById(Long id) {
        Laboratoire laboratoire = laboratoireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laboratoire not found"));
        return LaboratoireMapper.toLaboratoireDTO(laboratoire);
    }

    // Obtenir tous les laboratoires
    public List<LaboratoireDTO> getAllLaboratoires() {
        List<Laboratoire> laboratoires = laboratoireRepository.findAll();
        return laboratoires.stream()
                .map(LaboratoireMapper::toLaboratoireDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour un laboratoire
    public LaboratoireDTO updateLaboratoire(Long id, LaboratoireDTO laboratoireDTO) {
        Laboratoire existingLaboratoire = laboratoireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laboratoire not found"));

        existingLaboratoire.setNom(laboratoireDTO.getNom());
        existingLaboratoire.setLogo(laboratoireDTO.getLogo());
        existingLaboratoire.setNrc(laboratoireDTO.getNrc());
        existingLaboratoire.setActive(laboratoireDTO.isActive());
        existingLaboratoire.setDateActivation(laboratoireDTO.getDateActivation());

        Laboratoire updatedLaboratoire = laboratoireRepository.save(existingLaboratoire);
        return LaboratoireMapper.toLaboratoireDTO(updatedLaboratoire);
    }

    // Supprimer un laboratoire
    public void deleteLaboratoire(Long id) {
        Laboratoire laboratoire = laboratoireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laboratoire not found"));
        laboratoireRepository.deleteById(id);
    }
}
